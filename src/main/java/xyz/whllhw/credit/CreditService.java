package xyz.whllhw.credit;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.whllhw.task.State;
import xyz.whllhw.task.TaskUserRepository;

import java.util.List;

@Service
public class CreditService {
    @Autowired
    private CreditLogRepository creditLogRepository;

    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private TaskUserRepository taskUserRepository;


    public Integer getCredit(@NonNull String user) {
        return creditRepository.findTopByUser(user).getVal();
    }

    public List<CreditLogEntity> getLog(@NonNull String user) {
        return creditLogRepository.findAllByCreditId(
                creditRepository.findTopByUser(user).getId()
        );
    }

    public void initCredit(@NonNull String user, Integer val) {
        CreditEntity creditEntity = new CreditEntity();
        creditEntity.setUser(user);
        creditEntity.setVal(val);
        creditRepository.save(creditEntity);
    }

    private void doCredit(String user, String type) {
        CreditEntity creditEntity = creditRepository.findTopByUser(user);
        Integer x = 500 + 450 * taskUserRepository.countAllByUserAndState(user, State.FINISHED) / taskUserRepository.countAllByUser(user);
        addLog(creditEntity.getId(), x - creditEntity.getVal(), type);
        creditEntity.setVal(x);
        creditRepository.save(creditEntity);
    }


    private void addLog(Long creditId, Integer val, String type) {
        CreditLogEntity creditLogEntity = new CreditLogEntity();
        creditLogEntity.setCreditId(creditId);
        creditLogEntity.setCredit(val);
        creditLogEntity.setType(type);
        creditLogRepository.save(creditLogEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addCreditByFinishTask(String user) {
        doCredit(user, "完成任务");
    }

    @Transactional(rollbackFor = Exception.class)
    public void addCreditByFailedTask(String user) {
        doCredit(user, "任务失败");
    }
}
