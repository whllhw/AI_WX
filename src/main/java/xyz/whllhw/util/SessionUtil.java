package xyz.whllhw.util;

import xyz.whllhw.config.SecurityInterceptor;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static String getUserName(HttpSession httpSession) {
        return (String) httpSession.getAttribute(SecurityInterceptor.LOGIN_FLAG);
    }
}
