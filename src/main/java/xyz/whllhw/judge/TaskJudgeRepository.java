package xyz.whllhw.judge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TaskJudgeRepository extends JpaRepository<TaskJudgeEntity, Long> {
    Integer countAllByDataId(Long dataId);


    @Modifying
    @Transactional
    @Query(value = "update data_set set state = ?2 where id in (  select data_set.id from task_judge join " +
            "(select * from data_set where state = ?1) as d on task_judge.data_id = d.id " +
            "group by d.id having sum(task_judge.score) < 0.7 * count(1) ) ", nativeQuery = true)
    void setAllNeedAdminJudge(Integer fromState, Integer toState);
}
