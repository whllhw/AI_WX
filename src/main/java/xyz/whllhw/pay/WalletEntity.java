package xyz.whllhw.pay;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "wallet")
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 可提现
     */
    private Float money = 0f;
    /**
     * 目前的所有收益
     */
    private Float allMoney = 0f;

    @Column(nullable = false,unique = true)
    private String user;
    /**
     * 乐观锁，假设该行数据不会发生冲突。
     * 每次要更新时版本+1，若提交的数据版本号大于当前数据库的版本号则更新，否则事务失败
     */
    @Version
    private Integer version;
}
