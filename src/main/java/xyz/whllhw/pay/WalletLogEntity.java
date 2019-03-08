package xyz.whllhw.pay;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "wallet_log")
public class WalletLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long walletId;
    /**
     * 交易金额
     */
    private Float money = 0f;
    /**
     * 交易类型
     */
    private String type;
    @CreationTimestamp
    private Timestamp time;
}
