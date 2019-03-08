package xyz.whllhw.credit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<CreditEntity, Long> {
    CreditEntity findTopByUser(String user);
}
