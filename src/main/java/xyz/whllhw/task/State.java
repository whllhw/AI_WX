package xyz.whllhw.task;

public enum State {
    /**
     *
     */
    FAILED("未完成"),
    /**
     *
     */
    WAIT_SUBMIT("进行中"),
    /**
     *
     */
    WAIT_MACHINE_JUDGE("待机器审核"),
    /**
     *
     */
    NEED_HUMANS_JUDGE("待多人审核"),
    /**
     *
     */
    NEED_ADMIN_JUDGE("待管理审核"),
    /**
     *
     */
    FINISHED("已完成");
    String name;

    State(String name) {
        this.name = name;
    }
}
