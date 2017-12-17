package com.athena.aspect;

import com.athena.model.User;
import com.athena.service.message.SystemMessageService;
import io.jsonwebtoken.lang.Strings;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Tommy on 2017/12/17.
 */
@Aspect
@Component
public class UserUpdateAspect {
    private SystemMessageService systemMessageService;
    private String[] receiverAuthorities;

    @Autowired
    public UserUpdateAspect(@Value("${message.system.receiver}") String receiverAuthority, SystemMessageService systemMessageService) {
        this.receiverAuthorities = Strings.split(receiverAuthority, ",");
        this.systemMessageService = systemMessageService;
    }


    @AfterReturning(pointcut = "execution(com.athena.model.User com.athena.service..UserService.update(com.athena.model.User))", returning = "retVal")
    public void afterUserUpdate(JoinPoint joinPoint, Object retVal) {
        //todo:test
        User user = (User) retVal;
        List<SimpleGrantedAuthority> userAuthorities = user.getAuthority();
        for (String receiverAuthority : receiverAuthorities) {
            if (userAuthorities.indexOf(new SimpleGrantedAuthority(receiverAuthority)) != -1) {
                // if the user authority is in receiver authority
                // then conduct a update to receiver list
                List<User> receiver = this.systemMessageService.getReceiver();
                if (receiver.indexOf(user) == -1) {
                    //if not, add user
                    receiver.add(user);
                    this.systemMessageService.setReceiver(receiver);
                }
                break;
            }
        }

    }
}
