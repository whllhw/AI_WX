package xyz.whllhw.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRequireRepository extends JpaRepository<TaskRequireEntity, Long> {
    List<TaskRequireEntity> findAllByTaskId(Long taskId);
}
