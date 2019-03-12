package xyz.whllhw.weixin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findTopByOpenId(String openid);
}
