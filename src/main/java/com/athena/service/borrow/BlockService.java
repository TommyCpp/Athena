package com.athena.service.borrow;

import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.model.BlockedUser;
import com.athena.model.User;
import com.athena.repository.jpa.BlockedUserRepository;
import com.athena.repository.jpa.UserRepository;
import com.athena.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Tommy on 2017/11/27.
 */
@Service
public class BlockService {

    private BlockedUserRepository blockedUserRepository;
    private UserRepository userRepository;

    @Autowired
    public BlockService(BlockedUserRepository blockedUserRepository, UserRepository userRepository) {
        this.blockedUserRepository = blockedUserRepository;
        this.userRepository = userRepository;
    }

    public void blockUser(Long userId, User handler) throws ResourceNotFoundByIdException {
        User user = this.userRepository.findOne(userId);
        EntityUtil.requireEntityNotNull(user);
        this.blockUser(user, handler);
    }


    private void blockUser(User user, User handler) {
        BlockedUser blockedUser = new BlockedUser(user);
        blockedUser.setHandler(handler);
        this.blockedUserRepository.save(blockedUser);
    }

    public void unBlockUser(Long userId, User handler) throws ResourceNotFoundByIdException {
        BlockedUser blockedUser = this.blockedUserRepository.findOne(userId);
        EntityUtil.requireEntityNotNull(blockedUser);
        this.blockedUserRepository.delete(blockedUser);
    }
}
