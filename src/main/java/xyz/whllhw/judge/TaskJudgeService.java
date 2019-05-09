package xyz.whllhw.judge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.whllhw.dao.DataSetDao;
import xyz.whllhw.domain.DataSet;
import xyz.whllhw.task.*;
import xyz.whllhw.util.DataUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class TaskJudgeService {
    @Autowired
    private TaskJudgeRepository taskJudgeRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private DataSetDao dataRepository;
    @Autowired
    private TaskUserRepository taskUserRepository;
    @Autowired
    private TaskUserService taskUserService;

    /**
     * 得到机器应该审核的数据
     */
    public List<DataSet> getMachineJudge(String label) {
        return dataRepository.findAllByStateAndLabel(State.WAIT_MACHINE_JUDGE, label);
    }


    /**
     * 机器审核不过时。需要发任务给用户审核
     */
    @Transactional(rollbackFor = Exception.class)
    public void machineJudge(Long dataId, String label, Float score) {
        TaskJudgeEntity taskJudgeEntity = new TaskJudgeEntity();
        taskJudgeEntity.setDataId(dataId);
        taskJudgeEntity.setFromType("机器");
        taskJudgeEntity.setScore(score);
        taskJudgeEntity.setUser("MACHINE");
        taskJudgeEntity.setLabel(label);
        taskJudgeRepository.save(taskJudgeEntity);
        DataSet dataEntity = dataRepository.getOne(dataId);
        TaskUserEntity taskUserEntity = taskUserRepository.findTopByTaskIdAndUser(dataEntity.getTaskId(), dataEntity.getUserId());
        // 大于0.8直接认可
        // 在0.2到0.8范围内需要人工判断
        // 小于0.2直接判断
        State toState = null;
        if (score > 0.8) {
            toState = State.FINISHED;
        } else if (score > 0.2) {
            // 添加审核任务
            toState = State.NEED_HUMANS_JUDGE;
            addJudge(dataId);
        } else {
            toState = State.FAILED;
        }
        dataEntity.setState(toState);
        taskUserService.setTaskWithState(taskUserEntity, toState);
        dataRepository.saveAndFlush(dataEntity);
    }

    /**
     * 机器审核后不符合要求，再发布给用户审核。
     * 接用户时同样调用takeTask
     * TODO:未检查信用分
     */
    @Transactional(rollbackFor = Exception.class)
    public void addJudge(Long dataId) {
        TaskEntity task = taskRepository.findById(dataRepository.getOne(dataId).getTaskId()).get();
        TaskEntity newTask = new TaskEntity();
        newTask.setType(TaskType.VOICE_ANNOTATION);
        newTask.setNumber(task.getNumber());
        newTask.setRemainNumber(task.getNumber());
        newTask.setTitle(task.getTitle().replace("采集", "标注"));
        newTask.setSubTitle(task.getSubTitle().replace("采集", "标注"));
        newTask.setMoney(task.getMoney());
        newTask.setText(task.getText().replace("采集", "标注"));
        newTask.setDataType(task.getDataType());
        newTask.setDataId(dataId);
        newTask.setLabel(task.getLabel());
        newTask.setOriginId(task.getId());
        taskRepository.save(newTask);
    }

    /**
     * 按标记任务的id得到数据，
     * 由于有很多条，需要得知哪些是不用审核的
     */
    public InputStream getJudgeFile(Long taskId) throws FileNotFoundException {
        TaskEntity task = taskRepository.getOne(taskId);
        Long dataId = task.getDataId();
        DataSet dataEntity = dataRepository.getOne(dataId);
        return DataUtil.getFile(dataEntity.getFileName());
    }


    public InputStream getFileByDataId(Long dataId) throws FileNotFoundException {
        return DataUtil.getFile(dataRepository.getOne(dataId).getFileName());
    }

    /**
     * 用户提交标注
     */
    @Transactional(rollbackFor = Exception.class)
    public void submitJudgeFromUser(Long taskId, Float score, String user) {
        TaskUserEntity taskUserEntity = taskUserRepository.findTopByTaskIdAndUser(taskId, user);
        if (taskUserEntity == null) {
            log.info("未接受任务，无法提交标注");
            return;
        }
        taskUserEntity.setState(State.FINISHED);
        taskUserRepository.save(taskUserEntity);
        TaskJudgeEntity judgeEntity = new TaskJudgeEntity();
        TaskEntity taskEntity = taskRepository.getOne(taskId);
        judgeEntity.setLabel(taskEntity.getLabel());
        judgeEntity.setScore(score);
        judgeEntity.setDataId(taskEntity.getDataId());
        judgeEntity.setFromType("用户");
        judgeEntity.setUser(user);
        taskJudgeRepository.save(judgeEntity);
        // 任务已经提交全部标注
        // 判断是否需要交给管理员审核
        // 并根据结果设置任务的状态
        if (taskEntity.getNumber().equals(taskJudgeRepository.countAllByDataId(judgeEntity.getDataId()))) {
            Integer before = dataRepository.countAllByState(State.NEED_ADMIN_JUDGE);
            taskJudgeRepository.setAllNeedAdminJudge(State.NEED_HUMANS_JUDGE.ordinal(), State.NEED_ADMIN_JUDGE.ordinal());
            Integer after = dataRepository.countAllByState(State.NEED_ADMIN_JUDGE);

            List<TaskUserEntity> idList = taskUserRepository.findAllByTaskId(taskId);
            DataSet dataEntity = dataRepository.getOne(taskEntity.getDataId());
            TaskUserEntity taskUser = taskUserRepository.findTopByTaskIdAndUser(taskEntity.getOriginId(), dataEntity.getUserId());
            // 当前数据不需要交管理员：FINISHED
            // 当前数据交给管理员：    NEED_ADMIN_JUDGE
            State toState = after.compareTo(before) == 0 ? State.FINISHED : State.NEED_ADMIN_JUDGE;
            idList.forEach(s -> taskUserService.setTaskWithState(s, toState));
            taskUserService.setTaskWithState(taskUser, toState);
        }
    }

    /**
     * 得到管理员需要审核的数据
     */
    public List<DataSet> getAdminJudge() {
        return dataRepository.findAllByState(State.NEED_ADMIN_JUDGE);
    }

    /**
     * 管理员提交
     */
    @Transactional(rollbackFor = Exception.class)
    public void submitFromAdmin(Long dataId, Float score) {
        // 保存评分信息
        TaskJudgeEntity taskJudgeEntity = new TaskJudgeEntity();
        DataSet dataEntity = dataRepository.getOne(dataId);
        taskJudgeEntity.setLabel(dataEntity.getLabel());
        taskJudgeEntity.setScore(score);
        taskJudgeEntity.setDataId(dataId);
        taskJudgeEntity.setFromType("管理员");
        taskJudgeEntity.setUser("admin");
        taskJudgeRepository.save(taskJudgeEntity);
        // 更改原来的任务状态
        dataEntity.setState(State.FINISHED);
        dataRepository.saveAndFlush(dataEntity);
        State toState = score < 0.7 ? State.FAILED : State.FINISHED;
        // 将原始任务结束
        taskUserService.setTaskWithState(dataEntity.getTaskId(), dataEntity.getUserId(), toState);
        // 将衍生任务结束
        TaskEntity taskEntity = taskRepository.findTopByOriginId(dataEntity.getTaskId());
        List<TaskUserEntity> taskList = taskUserRepository.findAllByTaskId(taskEntity.getId());
        taskList.forEach(s -> taskUserService.setTaskWithState(s, State.FINISHED));
    }

}
