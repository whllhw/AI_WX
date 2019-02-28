package xyz.whllhw.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonPlus {
    public final static Gson GSON = new GsonBuilder()
            .serializeNulls()  // null字段不忽略
            .setDateFormat("yyyy-MM-dd HH:mm:ss")  //设置日期的格式
            .disableHtmlEscaping()  //防止对网址乱码 忽略对特殊字符的转换
            .create();
}