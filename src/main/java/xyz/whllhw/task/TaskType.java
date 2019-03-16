package xyz.whllhw.task;

public enum TaskType {

    /**
     *
     */
    VOICE_COLLECTION("语音采集"),
    /**
     *
     */
    VOICE_ANNOTATION("语音标注");
    String name;

    TaskType(String name) {
        this.name = name;
    }
}
