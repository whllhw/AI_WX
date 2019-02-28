package xyz.whllhw.weixin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.whllhw.weixin.entity.UserEntity;
import xyz.whllhw.weixin.repository.UserRepository;


@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserEntity getUserInfo(String openid){
        return userRepository.findTopByOpenId(openid);
    }
}
