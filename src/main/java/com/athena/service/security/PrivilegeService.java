package com.athena.service.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 2018/8/13.
 */
@Service
public class PrivilegeService {
    private List<String> privileges;

    @Autowired
    public PrivilegeService(@Value("${privilege.sequence}") String privilegeSequence) {
        String[] privilegesArray = StringUtils.split(privilegeSequence, ',');
        this.privileges = new ArrayList<>();
        for (int i = 0; i < privilegesArray.length; i++) {
            this.privileges.add("ROLE_" + privilegesArray[i]);
        }
    }

    public List<String> getPrivilegePriority() {
        return this.privileges;
    }

    public boolean isLegalPrivilege(String privilege) {
        return privilege.contains(privilege);
    }

    public boolean isLegalPrivilege(List<String> privileges) {
        if (privileges.size() > this.privileges.size()) {
            // if users has more privileges than all privileges, then return false
            return false;
        } else {
            for (String privilege : privileges) {
                if(!this.privileges.contains(privilege)){
                    return false;
                }
            }
            return true;
        }
    }

}
