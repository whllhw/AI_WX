package xyz.whllhw.weixin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeixinBindRepository extends JpaRepository<WeiXinBindEntity, Long> {
    @Query("select openId from #{#entityName} where openId = ?1")
    String findUserIdByOpenId(String openid);
}
