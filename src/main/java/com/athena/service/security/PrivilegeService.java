package com.athena.service.security;

import com.athena.exception.http.PermissionRequiredException;
import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.model.security.Account;
import com.athena.model.security.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tommy on 2018/8/13.
 */
@Service
public class PrivilegeService {
    private List<String> privileges;
    private Map<String, Integer> privilegeMap; // key -> privilege name, value -> privilege level, the higher means have more permission.


    @Autowired
    public PrivilegeService(@Value("${privilege.sequence}") String privilegeSequence) {
        this.privilegeMap = new HashMap<>();
        String[] privilegesArray = StringUtils.split(privilegeSequence, ',');
        this.privileges = new ArrayList<>();
        for (int i = 0; i < privilegesArray.length; i++) {
            String privilegeName = "ROLE_" + privilegesArray[i];
            this.privileges.add(privilegeName);
            privilegeMap.put(privilegeName, privilegesArray.length - i);
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
                if (!this.privileges.contains(privilege)) {
                    return false;
                }
            }
            return true;
        }
    }

    public boolean isCurrentUserCanOperateOn(User modifiedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currentUser = (Account) authentication.getPrincipal();
        return this.isCurrentUserCanOperateOn(modifiedUser, currentUser.getUser());
    }

    public boolean isCurrentUserCanOperateOn(User modifiedUser, User modifyingUser) {
        Integer privilegeOfModifyingUser = this.getBiggestPrivilegeLevel(modifyingUser);
        Integer privilegeOfModifiedUser = this.getBiggestPrivilegeLevel(modifiedUser);
        return privilegeOfModifyingUser > privilegeOfModifiedUser;
    }

    public String getBiggestPrivilegeName(User user) {
        List<String> privileges = user.getIdentity();
        if (privileges.size() < 1) {
            throw new IllegalArgumentException("Account should have at least one privilege");
        }
        int maxPrivilegeLevel = -1;
        String maxPrivilege = null;
        for (String privilegeName : privileges) {
            if (this.privilegeMap.get(privilegeName) > maxPrivilegeLevel) {
                maxPrivilegeLevel = this.privilegeMap.get(privilegeName);
                maxPrivilege = privilegeName;
            }
        }
        return maxPrivilege;
    }

    public Integer getBiggestPrivilegeLevel(User user) {
        List<String> privileges = user.getIdentity();
        if (privileges.size() < 1) {
            throw new IllegalArgumentException("Account should have at least one privilege");
        }
        int maxPrivilegeLevel = -1;
        for (String privilegeName : privileges) {
            if (this.privilegeMap.get(privilegeName) > maxPrivilegeLevel) {
                maxPrivilegeLevel = this.privilegeMap.get(privilegeName);
            }
        }
        return maxPrivilegeLevel;
    }

    public boolean privilegeBigger(String privilege1, String privilege2) {
        int privilege1Index = this.privileges.indexOf(privilege1);
        int privilege2Index = this.privileges.indexOf(privilege2);
        if (privilege1Index == -1 || privilege2Index == -1) {
            throw new IllegalArgumentException("privilege passed in does not exist in privilege sequence");
        } else {
            return privilege1Index < privilege2Index;
        }
    }

}
