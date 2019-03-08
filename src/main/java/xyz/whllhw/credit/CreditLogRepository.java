package xyz.whllhw.credit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditLogRepository extends JpaRepository<CreditLogEntity, Long> {

    List<CreditLogEntity> findAllByCreditId(Long id);

}
