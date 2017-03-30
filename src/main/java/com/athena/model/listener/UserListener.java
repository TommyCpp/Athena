package com.athena.model.listener;

import com.athena.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.PrePersist;

/**
 * Created by tommy on 2017/3/27.
 */
public class UserListener {

    private PasswordEncoder passwordEncoder;

    public UserListener(){
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @PrePersist
    public void setPassword(User user){
        //Encrypt the password before store in database
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
