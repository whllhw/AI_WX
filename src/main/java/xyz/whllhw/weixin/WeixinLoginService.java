package xyz.whllhw.weixin;

import com.alibaba.fastjson.JSON;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.whllhw.util.SessionUtil;
import xyz.whllhw.weixin.form.WeiXinBindForm;
import xyz.whllhw.weixin.form.WeixinSessionForm;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author jax
 */
@Service
@Log4j2
public class WeixinLoginService {

    @Value("${xyz.whllhw.weixin.appid}")
    private String appId;
    @Value("${xyz.whllhw.weixin.secret}")
    private String secret;
    private final WeixinBindRepository weixinBindRepository;
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final InitService initService;

    @Autowired
    public WeixinLoginService(WeixinBindRepository weixinBindRepository, UserRepository userRepository, HttpSession httpSession, InitService initService) {
        this.weixinBindRepository = weixinBindRepository;
        this.userRepository = userRepository;
        this.httpSession = httpSession;
        this.initService = initService;
    }

    /**
     * @param code 微信提供的code
     *             得到该微信号绑定的账号
     */
    private String getBindUserId(String code) throws IOException, WeixinException {
        var weixinSession = code2Session(code);
        var openid = weixinSession.getOpenid();
        return weixinBindRepository.findUserIdByOpenId(openid);
    }

    /**
     * @param code 微信提供的code
     *             直接使用微信进行登录，调用登录服务进行登录，设置session、下发token等操作
     */
    public void login(String code) throws WeixinException, IOException {
        var userid = getBindUserId(code);
        if (userid == null) {
            throw new WeixinException("该账号未注册");
        }
        SessionUtil.setLogin(httpSession, userid);
    }

    /**
     * 完善用户信息
     *
     * @param weiXinBindForm 提供微信code
     * @throws WeixinException 该微信号已注册
     */
    @Transactional(rollbackFor = Exception.class)
    public void bind(WeiXinBindForm weiXinBindForm) throws WeixinException, IOException {
        var weixinSession = code2Session(weiXinBindForm.getCode());
        var openid = weixinSession.getOpenid();
        if (weixinBindRepository.findUserIdByOpenId(openid) != null) {
            throw new WeixinException("该微信账号已注册");
        }
        WeiXinBindEntity weiXinBindEntity = new WeiXinBindEntity();
        weiXinBindEntity
                .setOpenId(weixinSession.getOpenid())
                .setSessionKey(weixinSession.getSession_key())
                .setUnionId(weixinSession.getUnionid());
        weixinBindRepository.save(weiXinBindEntity);
        userRepository.save(new UserEntity().setAvatarUrl(weiXinBindForm.getAvatarUrl())
                .setCity(weiXinBindForm.getCity())
                .setCountry(weiXinBindForm.getCountry())
                .setGender(weiXinBindForm.getGender())
                .setLanguage(weiXinBindForm.getLanguage())
                .setNickName(weiXinBindForm.getNickName())
                .setProvince(weiXinBindForm.getProvince())
                .setOpenId(openid));
        initService.initUser(openid);
        SessionUtil.setLogin(httpSession, openid);
    }

    /**
     * 调用微信API得到openid
     *
     * @param code 微信提供的code
     * @return openid、session_key
     * @throws IOException     网络请求错误
     * @throws WeixinException 引起错误原因如下：
     *                         1. 客户端提供非法code
     *                         1.1 使用了错误的appid、secret
     *                         2. 请求速度过快
     */
    private WeixinSessionForm code2Session(@NonNull String code) throws IOException, WeixinException {
        URL url = new URL(String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                appId, secret, code));
        URLConnection urlConnection = url.openConnection();
        WeixinSessionForm weixinSessionForm = JSON.parseObject(urlConnection.getInputStream(), WeixinSessionForm.class);
        if (weixinSessionForm.getErrcode() != null && weixinSessionForm.getErrcode() != 0) {
            log.error("微信服务请求失败：code:{},{}", weixinSessionForm.getErrcode(), weixinSessionForm.getErrMsg());
            throw new WeixinException(String.format("微信服务认证失败，code：%d，msg：%s", weixinSessionForm.getErrcode(), weixinSessionForm.getErrMsg()));
        }
        return weixinSessionForm;
    }
}
