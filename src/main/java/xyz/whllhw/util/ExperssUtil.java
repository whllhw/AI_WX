package xyz.whllhw.util;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.List;

public class ExperssUtil {

    public static List<String> parseLabel(String obj) {
        return (List<String>) JSON.parse(obj);
    }

    public static String toJsonLabel(String labels) {
        return JSON.toJSONString(Arrays.asList(labels.split("\\|")));
    }
}
