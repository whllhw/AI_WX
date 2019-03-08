package xyz.whllhw.task;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "task")
/**
 * 在领任务时用到需要悲观锁
 */
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String subTitle;
    /**
     * 总人数
     */
    @Column(nullable = false)
    private Integer number;
    /**
     * 剩余人数
     */
    @Column(nullable = false)
    private Integer remainNumber;
    @Column(nullable = false)
    private Float money;

    private String text;
    private String note;

    @CreationTimestamp
    private Timestamp createTime;
}
