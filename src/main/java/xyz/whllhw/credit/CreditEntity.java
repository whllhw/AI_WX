package xyz.whllhw.credit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "creadit")
@Entity
public class CreditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer val;
    @Column(unique = true,nullable = false)
    private String user;
    @Version
    private Integer version;
}
