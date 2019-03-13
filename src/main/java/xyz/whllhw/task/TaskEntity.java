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
    private String type;
    /**
     * 数据类型：音频
     */
    @Column(nullable = false)
    private String dataType;
    /**
     * 标签
     * 拼接字符串
     * 标签1|标签2
     */
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
    /**
     * 奖励细则
     */
    private String rewardNote;

    @Version
    private Integer version;
    @CreationTimestamp
    private Timestamp createTime;
}
