package xyz.whllhw.task.form;

import lombok.Getter;
import lombok.Setter;
import xyz.whllhw.task.State;
import xyz.whllhw.task.TaskEntity;
import xyz.whllhw.task.TaskType;

import java.sql.Timestamp;

@Getter
@Setter
public class UserTaskState extends TaskEntity {
    private State state;
    private Timestamp time;

    public static UserTaskState builder(Object[] obj) {
        UserTaskState u = new UserTaskState();
        u.setId(Long.valueOf(obj[0].toString()))
                .setTitle((String) obj[1])
                .setSubTitle((String) obj[2])
                .setType(TaskType.values()[Integer.valueOf(String.valueOf(obj[3]))])
                .setMoney((Float) obj[4])
                .setText((String) obj[5])
                .setRewardNote((String) obj[6])
                .setCreateTime(Timestamp.valueOf(obj[7].toString()));
        u.setTime(Timestamp.valueOf(obj[8].toString()));
        u.setState(State.values()[(Integer) obj[9]]);
        u.setNote((String) obj[10]);
        return u;
    }

}
