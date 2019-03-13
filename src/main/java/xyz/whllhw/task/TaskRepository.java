package xyz.whllhw.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Page<TaskEntity> findAllByType(String type, Pageable pageable);

    @Query(value = "select task.id,task.title,task.sub_title," +
            "task.type,task.money,task.text,task.reward_note," +
            "task.create_time,task_user.time,task_user.state from task join task_user on task.id = task_user.task_id " +
            "where task_user.user = ?1 and (task_user.state = ?2 or ?3 = true )", nativeQuery = true)
    List<Object[]> getUserTask(String user, String taskState, Boolean showAllFlag);

}
