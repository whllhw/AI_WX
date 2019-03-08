package xyz.whllhw.pay;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
