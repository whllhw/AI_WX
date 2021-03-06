package xyz.whllhw.task;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@lombok.experimental.Accessors(chain = true)
@Entity
@Table(name = "task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String subTitle;
    /**
     * 任务类型：数据收集、审核
     */
    @Column(nullable = false)
    private TaskType type;
    /**
     * 数据类型：音频
     */
    @Column(nullable = false)
    private String dataType;
    /**
     * 标签
     *
     */
    @Column(nullable = false)
    private String label;
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
    /**
     * 报酬
     */
    @Column(nullable = false)
    private Float money;
    /**
     * 描述
     */
    private String text;

    private String note;
    /**
     * 奖励细则
     */
    private String rewardNote;
    /**
     * 若是标注任务则有其数据
     */
    private Long dataId;
    /**
     * 原来的采集任务id
     */
    private Long originId;

    @Version
    private Integer version;
    @CreationTimestamp
    private Timestamp createTime;
}
