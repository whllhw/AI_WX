package xyz.whllhw.task;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "task_require")
@Entity
public class TaskRequireEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long taskId;
    /**
     * 需求类型
     */
    private String type;
    /**
     * 需求表达式，需要解析
     * >=100
     * in (标签1,标签2)
     */
    private String express;
}
