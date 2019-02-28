package xyz.whllhw.weixin.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeixinSessionForm {
    private String openid;
    private String session_key;
    private String unionid;
    private Integer errcode;
    private String errMsg;
}
