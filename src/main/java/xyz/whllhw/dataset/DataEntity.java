package xyz.whllhw.dataset;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import xyz.whllhw.task.State;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "data_set")
public class DataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long taskId;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String fileName;
    /**
     * 数据类型
     */
    private String type;
    /**
     * 数据状态
     */
    private State state;
    private String label;

    @CreationTimestamp
    private Timestamp time;
}

