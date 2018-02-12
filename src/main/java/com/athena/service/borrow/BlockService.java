package com.athena.service.borrow;

import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.model.security.BlockRecord;
import com.athena.model.security.User;
import com.athena.repository.jpa.BlockRecordRepository;
import com.athena.repository.jpa.UserRepository;
import com.athena.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Tommy on 2017/11/27.
 */
@Service
public class BlockService {
    private UserRepository userRepository;
    private BlockRecordRepository blockRecordRepository;

    /**
     * Instantiates a new Block service.
     *
     * @param userRepository        the user repository
     * @param blockRecordRepository the block record repository
     */
    @Autowired
    public BlockService(UserRepository userRepository, BlockRecordRepository blockRecordRepository) {
        this.userRepository = userRepository;
        this.blockRecordRepository = blockRecordRepository;
    }

    /**
     * Block user.
     *
     * @param userId  the user id
     * @param handler the handler
     * @throws ResourceNotFoundByIdException the resource not found by id exception
     */
    public void blockUser(Long userId, User handler) throws ResourceNotFoundByIdException {
        User user = this.userRepository.findOne(userId);
        EntityUtil.requireEntityNotNull(user);
        this.blockUser(user, handler);
    }


    /**
     * block user.
     *
     *
     * @param user the user instance
     * @param handler the handler instance
     *
     * */
    private void blockUser(User user, User handler) {
        BlockRecord blockRecord = new BlockRecord(user, handler);
        this.blockRecordRepository.save(blockRecord);
    }

    /**
     * Unblock user.
     *
     * @param userId  the user id
     * @param handler the handler
     * @throws ResourceNotFoundByIdException the resource not found by id exception
     */
    public void unBlockUser(Long userId, User handler) throws ResourceNotFoundByIdException {
        User user = this.userRepository.findOne(userId);
        EntityUtil.requireEntityNotNull(user);
        List<BlockRecord> blockRecords = user.getBlockRecords();
        for (BlockRecord blockRecord : blockRecords) {
            blockRecord.setEnabled(false);
            blockRecord.setUnblockHandler(handler);
        }
        this.userRepository.save(user);
    }
}
