package com.athena.model.listener;

import com.athena.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created by tommy on 2017/3/27.
 */
public class UserListener {

    private PasswordEncoder passwordEncoder;

    /**
     * Instantiates a new User listener.
     */
    public UserListener() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Encrypt the  password.
     *
     * @param user the user
     */
    @PrePersist
    public void setPassword(User user) {
        //Encrypt the password before store in database
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    /**
     * Encrypt the  password if the password has been changed.
     *
     * @param user the user
     */
    @PreUpdate
    public void resetPassword(User user) {
        if (user.getPassword().length() < 50) {
            // If the password length is greater than 50, indicate that the password has been encrypted
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }
}
