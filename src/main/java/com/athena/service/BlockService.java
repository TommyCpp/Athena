package com.athena.service;

import com.athena.exception.http.IdOfResourceNotFoundException;
import com.athena.model.BlockedUser;
import com.athena.model.User;
import com.athena.repository.jpa.BlockedUserRepository;
import com.athena.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by Tommy on 2017/11/27.
 */
@Service
public class BlockService {

    private final BlockedUserRepository blockedUserRepository;
    private final UserRepository userRepository;

    @Autowired
    public BlockService(BlockedUserRepository blockedUserRepository, UserRepository userRepository) {
        this.blockedUserRepository = blockedUserRepository;
        this.userRepository = userRepository;
    }

    public void blockUser(Long userId, User handler) throws IdOfResourceNotFoundException {
        User user = this.userRepository.findOne(userId);
        try {
            Objects.requireNonNull(user);
        } catch (NullPointerException e) {
            throw new IdOfResourceNotFoundException();
        }
        this.blockUser(user, handler);
    }


    private void blockUser(User user, User handler) throws IdOfResourceNotFoundException {
        BlockedUser blockedUser = new BlockedUser(user);
    }
}
