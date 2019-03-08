package xyz.whllhw.dataset;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "data")
public class DataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long taskId;
    @Column(nullable = false)
    private String user;
    /**
     * 数据类型
     */
    private String type;
    private String name;
    @CreationTimestamp
    private Timestamp time;
}

