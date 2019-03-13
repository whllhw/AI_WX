package xyz.whllhw.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskJudgeService {
    @Autowired
    private TaskJudgeRepository taskJudgeRepository;

    public void judeg(Long dataId, String user, Float score, String fromType) {
        TaskJudgeEntity judgeEntity = new TaskJudgeEntity();
        // TODO:check dataId
        judgeEntity.setDataId(dataId);
        judgeEntity.setFromType(fromType);
        judgeEntity.setScore(score);
        judgeEntity.setUser(user);
        taskJudgeRepository.save(judgeEntity);
    }


}
