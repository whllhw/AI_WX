package xyz.whllhw.credit;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "credit_log")
public class CreditLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long creditId;
    private Integer credit;
    private String type;
    @CreationTimestamp
    private Timestamp time;
}
