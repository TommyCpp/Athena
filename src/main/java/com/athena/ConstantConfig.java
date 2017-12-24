package com.athena;

import com.athena.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tommy on 2017/12/25.
 */
@Configuration
public class ConstantConfig {

    @Bean(name = "systemUsers")
    public Map<String, User> systemUsersConstant() {
        Map<String, User> systemUsers = new HashMap<>();
        User systemInfoUser = new User();
        systemInfoUser.setUsername("systemInfo");
        systemInfoUser.setEmail("info@test.com");
        systemInfoUser.setId(-1L);
        systemUsers.put("SystemInfo", systemInfoUser);
        return systemUsers;
    }
}
