package xyz.whllhw.pay;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.whllhw.credit.CreditService;

import java.util.List;

@Service
@Slf4j
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletLogRepository walletLogRepository;
    private final CreditService creditService;

    @Autowired
    public WalletService(WalletLogRepository walletLogRepository, WalletRepository walletRepository, CreditService creditService) {
        this.walletLogRepository = walletLogRepository;
        this.walletRepository = walletRepository;
        this.creditService = creditService;
    }

    List<WalletLogEntity> getLog(@NonNull String user) {
        return walletLogRepository.findAllByWalletId(
                walletRepository.findTopByUser(user).getId()
        );
    }

    WalletEntity getWalletEntity(@NonNull String user) {
        return walletRepository.findTopByUser(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void initWallet(@NonNull String user) {
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setAllMoney(0f);
        walletEntity.setMoney(0f);
        walletEntity.setUser(user);
        walletRepository.save(walletEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void finishTask(String user, Float val) {
        WalletEntity walletEntity = walletRepository.findTopByUser(user);
        Integer credit = creditService.getCredit(user);
        Float x = val * credit / 720.0f;
        walletEntity.setMoney(walletEntity.getMoney() + x);
        addLog(walletEntity.getId(), "完成任务", x);
        walletRepository.save(walletEntity);
    }

    private void addLog(Long walletId, String type, Float val) {
        WalletLogEntity walletLogEntity = new WalletLogEntity();
        walletLogEntity.setMoney(val);
        walletLogEntity.setType(type);
        walletLogEntity.setWalletId(walletId);
        walletLogRepository.save(walletLogEntity);
    }
}
