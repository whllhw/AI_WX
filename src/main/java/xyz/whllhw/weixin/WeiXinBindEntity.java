package xyz.whllhw.weixin;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "users_weixin")
@Getter
@Setter
@Accessors(chain = true)
public class WeiXinBindEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String openId;
    private String unionId;
    private String sessionKey;
}
