package xyz.whllhw.task;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.whllhw.dataset.DataRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskUserRepository taskUserRepository;
    @Autowired
    private TaskRequireRepository taskRequireRepository;
    @Autowired
    private DataRepository dataRepository;

    @Transactional(rollbackFor = Exception.class)
    public Long addTask(TaskEntity task, Integer credit) {
        taskRepository.saveAndFlush(task);
        // 设置标签，备用
//        TaskRequireEntity requireEntity = new TaskRequireEntity();
//        requireEntity.setExpress(ExperssUtil.toJsonLabel(task.getLabel()));
//        requireEntity.setType("标签");
//        requireEntity.setTaskId(task.getId());
//        taskRequireRepository.save(requireEntity);
        // 设置信用分
        TaskRequireEntity requireEntity1 = new TaskRequireEntity();
        requireEntity1.setType("信用分");
        requireEntity1.setTaskId(task.getId());
        requireEntity1.setExpress(credit.toString());
        taskRequireRepository.save(requireEntity1);
        return task.getId();
    }

    public Page<TaskEntity> getAllTask(TaskType taskType, Integer page) {
        if (taskType == null) {
            return taskRepository.findAll(PageRequest.of(page, 10));
        }
        return taskRepository.findAllByType(taskType, PageRequest.of(page, 10));
    }

    public TaskEntity getTask(@NonNull Long id) {
        return taskRepository.findById(id).get();
    }


}
