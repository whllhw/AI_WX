package xyz.whllhw.pay;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletLogRepository walletLogRepository;

    @Autowired
    public WalletService(WalletLogRepository walletLogRepository, WalletRepository walletRepository) {
        this.walletLogRepository = walletLogRepository;
        this.walletRepository = walletRepository;
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
}
