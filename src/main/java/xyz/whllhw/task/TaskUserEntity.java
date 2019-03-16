package xyz.whllhw.task;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "task_user")
public class TaskUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long taskId;
    @Column(nullable = false)
    private String user;
    /**
     * 任务状态
     * 进行中、审核中、已完成、未完成
     */
    private State state;

    @CreationTimestamp
    private Timestamp time;
    @Version
    private Integer version;
}
