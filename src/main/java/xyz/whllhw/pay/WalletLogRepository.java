package xyz.whllhw.pay;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletLogRepository extends JpaRepository<WalletLogEntity, Long> {
    List<WalletLogEntity> findAllByWalletId(Long walletId);
}
