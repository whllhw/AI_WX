package xyz.whllhw.credit;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditService {
    @Autowired
    private CreditLogRepository creditLogRepository;

    @Autowired
    private CreditRepository creditRepository;

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

}
