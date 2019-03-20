package xyz.whllhw.weixin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.whllhw.config.SecurityInterceptor;
import xyz.whllhw.util.CommonResponseForm;
import xyz.whllhw.util.SessionUtil;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@RestController
public class LoginController {

    private final WeixinLoginService weixinLoginService;
    private final UserService userService;
    private final HttpSession httpSession;

    @Autowired
    public LoginController(WeixinLoginService weixinLoginService, UserService userService, HttpSession httpSession) {
        this.weixinLoginService = weixinLoginService;
        this.userService = userService;
        this.httpSession = httpSession;
    }

    /**
     * （需要完善信息后）才可以直接使用微信登录
     */
    @PostMapping("/user/login")
    public CommonResponseForm wxLogin(@RequestBody Map<String, String> map) {
        try {
            weixinLoginService.login(map.get("code"));
            return CommonResponseForm.of204("login success");
        } catch (WeixinException e) {
            return CommonResponseForm.of400(e.getMessage());
        } catch (IOException e) {
            return CommonResponseForm.of500(e.getMessage());
        }
    }

    /**
     * 完善用户的信息
     */
    // @PostMapping("/user/reg")
    // public CommonResponseForm wxBind(@RequestBody WeiXinBindForm weiXinBindForm) {
    //     try {
    //         weixinLoginService.bind(weiXinBindForm);
    //         return CommonResponseForm.of204("bind success");
    //     } catch (WeixinException e) {
    //         return CommonResponseForm.of400(e.getMessage());
    //     } catch (IOException e) {
    //         return CommonResponseForm.of500(e.getMessage());
    //     }
    // }

    @GetMapping("/user/info")
    public CommonResponseForm userInfo() {
        return CommonResponseForm.of200("ok", userService.getUserInfo((String) httpSession.getAttribute(SecurityInterceptor.LOGIN_FLAG)));
    }

    @GetMapping("/user/test")
    public CommonResponseForm test() {
        SessionUtil.setLogin(httpSession, "oPCTy5L9OL-sHr2mYUXUsxZ5tkhc");
        SessionUtil.setAdmin(httpSession);
        return CommonResponseForm.of204("for test");
    }

}
