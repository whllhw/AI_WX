package xyz.whllhw.task;

public enum State {
    /**
     * ("未完成")
     */
    FAILED,
    /**
     * ("进行中")
     */
    WAIT_SUBMIT,
    /**
     * ("待机器审核")
     */
    WAIT_MACHINE_JUDGE,
    /**
     * ("待多人审核")
     */
    NEED_HUMANS_JUDGE,
    /**
     * ("待管理审核")
     */
    NEED_ADMIN_JUDGE,
    /**
     * ("已完成")
     */
    FINISHED
//    String name;
//
//    State(String name) {
//        this.name = name;
//    }
}

