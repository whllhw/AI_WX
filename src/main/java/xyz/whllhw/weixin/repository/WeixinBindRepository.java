package xyz.whllhw.weixin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.whllhw.weixin.entity.WeiXinBindEntity;

public interface WeixinBindRepository extends JpaRepository<WeiXinBindEntity, Long> {
    @Query("select openId from #{#entityName} where openId = ?1")
    String findUserIdByOpenId(String openid);
}
