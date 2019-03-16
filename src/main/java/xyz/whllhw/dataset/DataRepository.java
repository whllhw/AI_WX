package xyz.whllhw.dataset;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.whllhw.task.State;

import java.util.List;

public interface DataRepository extends JpaRepository<DataEntity, Long> {
    List<DataEntity> findAllByTaskIdAndState(Long taskId, State state);

    List<DataEntity> findAllByStateAndLabel(State state, String label);

    List<DataEntity> findAllByState(State state);

    Integer countAllByState(State state);
}
