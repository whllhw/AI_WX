package xyz.whllhw.task;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "task_judge")
public class TaskJudgeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long dataId;
    private Float score;
    @Column(nullable = false)
    private String user;
    /**
     * 给出评价的用户类型
     * 管理员、用户
     */
    private String fromType;

    @CreationTimestamp
    private Timestamp createTime;

    @Version
    private Integer version;
}
