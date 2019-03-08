package xyz.whllhw.pay;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
    WalletEntity findTopByUser(String user);
}
