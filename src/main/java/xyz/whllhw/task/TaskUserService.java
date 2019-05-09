package xyz.whllhw.task;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.whllhw.credit.CreditService;
import xyz.whllhw.dao.DataSetDao;
import xyz.whllhw.domain.DataSet;
import xyz.whllhw.label.LabelService;
import xyz.whllhw.pay.WalletService;
import xyz.whllhw.task.form.UserTaskState;
import xyz.whllhw.util.DataUtil;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
    private DataSetDao dataRepository;
    @Autowired
    private WalletService walletService;

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
//                case "标签":
//                    List<String> labels = ExperssUtil.parseLabel(t.getExpress());
//                    for (String l : labels) {
//                        if (userLabels.indexOf(l) < 0) {
//                            return false;
//                        }
//                    }
//                    break;
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
        taskUserEntity.setState(State.WAIT_SUBMIT);
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
        // TODO:重复提交
        // if (!State.WAIT_SUBMIT.equals(taskUser.getSal())) {
        //     return -2L;
        // }
        TaskEntity taskEntity = taskRepository.getOne(taskId);
        DataSet dataEntity = new DataSet();
        dataEntity.setLabel(taskEntity.getLabel());
        dataEntity.setTaskId(taskId);
        dataEntity.setType(taskEntity.getDataType());
        dataEntity.setUserId(user);
        dataEntity.setState(State.WAIT_MACHINE_JUDGE);
        dataEntity.setFileName(DataUtil.saveFile(file, taskId, user, taskEntity.getDataType()));
        dataRepository.saveAndFlush(dataEntity);
        taskUser.setState(State.WAIT_MACHINE_JUDGE);
        taskUserRepository.save(taskUser);
        return dataEntity.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void setTaskWithState(TaskUserEntity taskUser, State state) {
        doSetState(taskUser, state);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setTaskWithState(Long taskId, String user, State state) {
        TaskUserEntity taskUser = taskUserRepository.findTopByTaskIdAndUser(taskId, user);
        doSetState(taskUser, state);
    }

    private void doSetState(TaskUserEntity taskUser, State state) {
        String user = taskUser.getUser();
        TaskEntity task = taskRepository.getOne(taskUser.getTaskId());
        switch (state) {
            case FINISHED:
                taskUser.setState(State.FINISHED);
                taskUserRepository.save(taskUser);
                creditService.addCreditByFinishTask(user);
                walletService.finishTask(user, task.getMoney());
                break;
            case FAILED:
                taskUser.setState(State.FAILED);
                taskUserRepository.save(taskUser);
                creditService.addCreditByFailedTask(user);
                break;
            case NEED_HUMANS_JUDGE:
                taskUser.setState(state);
                taskUserRepository.save(taskUser);
                break;
            case NEED_ADMIN_JUDGE:
                taskUser.setState(state);
                taskUserRepository.save(taskUser);
                break;
            default:
                log.error("错误的State取值：{}", state.name());
        }
    }

    public List<UserTaskState> getUserTask(@NonNull String user) {
        return taskRepository.getUserTask(user, null, true).stream()
                .map(UserTaskState::builder).collect(Collectors.toList());
    }

    public List<UserTaskState> getUserTaskByState(@NonNull String user, @NonNull State state) {
        return taskRepository.getUserTask(user, state.ordinal(), false).stream()
                .map(UserTaskState::builder).collect(Collectors.toList());
    }
}
