package xyz.whllhw.util;

import xyz.whllhw.config.SecurityInterceptor;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static String getUserName(HttpSession httpSession) {
        return (String) httpSession.getAttribute(SecurityInterceptor.LOGIN_FLAG);
    }

    public static void setLogin(HttpSession httpSession, String userId) {
        httpSession.setAttribute(SecurityInterceptor.LOGIN_FLAG, userId);
    }
}
