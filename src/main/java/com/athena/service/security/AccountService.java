package com.athena.service.security;

import com.athena.exception.internal.AccountNotFoundException;
import com.athena.model.security.Account;
import com.athena.model.security.User;
import com.athena.repository.jpa.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tommy on 2017/3/24.
 */
@Service
public class AccountService {

    private UserRepository userRepository;
    private List<String> privilegePriority; // Array, denotes the privilege priority among roles.

    @Autowired
    public AccountService(UserRepository userRepository, @Value("${privilege.sequence}") String privilegeSequence) {
        this.userRepository = userRepository;
        String[] privileges = StringUtils.split(privilegeSequence, ',');
        this.privilegePriority = new ArrayList<>();
        for (int i = 0; i < privileges.length; i++) {
            this.privilegePriority.add("ROLE_" + privileges[i]);
        }
    }

    public Account loadAccountById(Long id) throws AuthenticationException {
        User user = userRepository.findOne(id);
        if (user == null) {
            //Not found the user
            throw new AccountNotFoundException();
        } else {
            return new Account(user);

        }
    }

    /**
     * @param account1 account
     * @param account2 the other account
     * @return is the privilege of account1 is bigger than account2
     */
    public boolean privilegeBigger(Account account1, Account account2) {
        return (this.privilegePriority.indexOf(this.findBiggestPrivilege(account1)) < this.privilegePriority.indexOf(this.findBiggestPrivilege(account2)));
    }

    /**
     * @param account account
     * @param privilege privilege string to compare
     * @return is the privilege of account is bigger than param privilege
     * @throws IllegalArgumentException privilege string that passed in is not in privilege sequence define in config file
     */
    public boolean privilegeBigger(Account account, String privilege) {
        if (this.privilegePriority.indexOf(privilege) == -1) {
            throw new IllegalArgumentException("privilege passed in does not exist in privilege sequence");
        }
        return (this.privilegePriority.indexOf(this.findBiggestPrivilege(account)) < this.privilegePriority.indexOf(privilege));
    }

    /**
     * @param account account
     * @return the biggest privilege string of account
     * @throws IllegalArgumentException account contains no privilege
     */
    private String findBiggestPrivilege(Account account) {
        String result = "";
        if (account.getAuthorities().size() < 1) {
            throw new IllegalArgumentException("Account should have at least one privilege");
        }
        for (GrantedAuthority authority : account.getAuthorities()) {
            if (result.equals("")) {
                result = authority.getAuthority();
            } else if (this.privilegePriority.indexOf(authority.getAuthority()) < this.privilegePriority.indexOf(result)) {
                result = authority.getAuthority();
            }
        }
        return result;
    }
}
