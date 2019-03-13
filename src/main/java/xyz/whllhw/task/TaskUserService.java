package xyz.whllhw.task;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.whllhw.credit.CreditService;
import xyz.whllhw.dataset.DataEntity;
import xyz.whllhw.dataset.DataRepository;
import xyz.whllhw.label.LabelService;
import xyz.whllhw.task.form.UserTaskState;
import xyz.whllhw.util.DataUtil;
import xyz.whllhw.util.ExperssUtil;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskUserService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskUserRepository taskUserRepository;
    @Autowired
    private TaskRequireRepository taskRequireRepository;
    @Autowired
    private LabelService labelService;
    @Autowired
    private CreditService creditService;
    @Autowired
    private DataRepository dataRepository;

    /**
     * 接任务
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean takeTask(@NonNull Long id, @NonNull String userId) {
        // 验证是否重复接任务
        if (taskUserRepository.countAllByTaskIdAndUser(id, userId) > 0) {
            return false;
        }

        // 是否还有空位，乐观锁可防止并发接任务
        TaskEntity task = taskRepository.getOne(id);
        Integer number = task.getRemainNumber();
        if (number < 1) {
            return false;
        }
        number = number - 1;
        task.setRemainNumber(number);
        taskRepository.save(task);

        // 验证是否符合条件
        List<TaskRequireEntity> requireEntities = taskRequireRepository.findAllByTaskId(id);
        List<String> userLabels = labelService.getLabel(userId);
        for (TaskRequireEntity t : requireEntities) {
            switch (t.getType()) {
                case "标签":
                    List<String> labels = ExperssUtil.parseLabel(t.getExpress());
                    for (String l : labels) {
                        if (userLabels.indexOf(l) < 0) {
                            return false;
                        }
                    }
                    break;
                case "信用分":
                    Integer credit = creditService.getCredit(userId);
                    if (credit.compareTo(Integer.valueOf(t.getExpress())) < 0) {
                        return false;
                    }
                    break;
                default:
            }
        }
        TaskUserEntity taskUserEntity = new TaskUserEntity();
        taskUserEntity.setTaskId(id);
        taskUserEntity.setUser(userId);
        taskUserEntity.setState("进行中");
        taskUserRepository.save(taskUserEntity);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long submitTask(Long taskId, String user, MultipartFile file) throws IOException {
        // 是否接受了任务
        Integer counter = taskUserRepository.countAllByTaskIdAndUser(taskId, user);
        if (counter == 0) {
            return -1L;
        }
        TaskUserEntity taskUser = taskUserRepository.findTopByTaskIdAndUser(taskId, user);
        // 重复提交
        if (!"进行中".equals(taskUser.getState())) {
            return -2L;
        }
        TaskEntity taskEntity = taskRepository.getOne(taskId);
        DataEntity dataEntity = new DataEntity();
        dataEntity.setTaskId(taskId);
        dataEntity.setType(taskEntity.getType());
        dataEntity.setUserId(user);
        dataEntity.setState("待审核");
        dataEntity.setFileName(DataUtil.saveFile(file, taskId, user, taskEntity.getDataType()));
        dataRepository.saveAndFlush(dataEntity);
        taskUser.setState("待审核");
        taskUserRepository.save(taskUser);
        return dataEntity.getId();
    }

    public List<UserTaskState> getUserTask(@NonNull String user) {
        return taskRepository.getUserTask(user, "", true).stream()
                .map(UserTaskState::builder).collect(Collectors.toList());
    }

    public List<UserTaskState> getUserTaskByState(@NonNull String user, @NonNull String state) {
        return taskRepository.getUserTask(user, state, false).stream()
                .map(UserTaskState::builder).collect(Collectors.toList());
    }
}
