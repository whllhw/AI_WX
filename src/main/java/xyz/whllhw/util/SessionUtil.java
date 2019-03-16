package xyz.whllhw.util;

import xyz.whllhw.config.SecurityInterceptor;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    private static final String ADMIN_FLAG = "admin";

    public static String getUserName(HttpSession httpSession) {
        return (String) httpSession.getAttribute(SecurityInterceptor.LOGIN_FLAG);
    }

    public static void setLogin(HttpSession httpSession, String userId) {
        httpSession.setAttribute(SecurityInterceptor.LOGIN_FLAG, userId);
    }

    public static boolean isAdmin(HttpSession httpSession) {
        Object o = httpSession.getAttribute(ADMIN_FLAG);
        if (o instanceof Integer) {
            return (Integer) o == 1;
        }
        return false;
    }

    public static void setAdmin(HttpSession httpSession) {
        httpSession.setAttribute(ADMIN_FLAG, 1);
    }
}
