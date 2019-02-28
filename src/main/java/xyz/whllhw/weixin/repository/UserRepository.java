package xyz.whllhw.weixin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.whllhw.weixin.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findTopByOpenId(String openid);
}
