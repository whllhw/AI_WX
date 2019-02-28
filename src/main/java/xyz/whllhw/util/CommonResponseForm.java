package xyz.whllhw.util;

import org.springframework.data.domain.Page;

import javax.servlet.http.HttpSession;

/**
 * 返回前端数据统一接口，组装数据。
 * 如无特殊需求，建议使用本类进行组装。
 * 编码原则：DRY
 * don't repeat yourself
 * @author lhw
 */
public class CommonResponseForm {
    private Integer status;
    private String message;
    private Object data;

    public CommonResponseForm(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public CommonResponseForm(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static CommonResponseForm ofRetry(String msg) {
        return new CommonResponseForm(1, msg);
    }

    public static CommonResponseForm of401(HttpSession httpSession, String url) {
        return new CommonResponseForm(401,
                "用户:" + httpSession.getAttribute("username") + "权限不足 at:" +
                        url);
    }

    public static CommonResponseForm of403(String url) {
        return new CommonResponseForm(403, "请登录后重试:" + url);
    }

    public static CommonResponseForm of200(String message, Object data) {
        return new CommonResponseForm(0, message, data);
    }

    public static CommonResponseForm of204(String message) {
        return new CommonResponseForm(0, message);
    }

    public static CommonResponseForm of400(String message) {
        return new CommonResponseForm(400, message);
    }

    public static CommonResponseForm ofPageable(String message,Page<?> p){
        return new CommonResponseForm(0,message,new CommonPageableResponseForm(p));
    }

    public static CommonResponseForm of404(String message) {
        return new CommonResponseForm(404, "找不到id:" + message);
    }

    public static CommonResponseForm of500(String message) {
        return new CommonResponseForm(500, message);
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
