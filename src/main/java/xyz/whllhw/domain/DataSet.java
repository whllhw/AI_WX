package xyz.whllhw.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xyz.whllhw.task.State;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class DataSet {
    private Long id;

    private Long taskId;

    private String userId;

    private String fileName;

    private String type;

    private State state;

    private String label;

    private Timestamp time = Timestamp.valueOf(LocalDateTime.now());

}
