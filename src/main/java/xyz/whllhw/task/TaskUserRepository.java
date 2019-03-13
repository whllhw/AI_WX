package xyz.whllhw.task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskUserRepository extends JpaRepository<TaskUserEntity, Long> {
    Integer countAllByTaskIdAndUser(Long taskId, String user);

    TaskUserEntity findTopByTaskIdAndUser(Long taskId, String user);
}
