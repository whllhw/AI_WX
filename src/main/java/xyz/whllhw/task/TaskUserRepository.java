package xyz.whllhw.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskUserRepository extends JpaRepository<TaskUserEntity, Long> {
    Integer countAllByTaskIdAndUser(Long taskId, String user);

    TaskUserEntity findTopByTaskIdAndUser(Long taskId, String user);

    Integer countAllByUser(String user);

    Integer countAllByUserAndState(String user, State state);

    List<TaskUserEntity> findAllByTaskId(Long taskId);
}
