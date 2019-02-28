package xyz.whllhw.weixin.form;

import lombok.Getter;
import lombok.Setter;
import xyz.whllhw.weixin.entity.UserEntity;

@Getter
@Setter
public class WeiXinBindForm extends UserEntity {
    private String code;
}
