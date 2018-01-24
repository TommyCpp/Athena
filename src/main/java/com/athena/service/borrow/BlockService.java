package com.athena.service.borrow;

import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.model.BlockRecord;
import com.athena.model.User;
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

    @Autowired
    public BlockService(UserRepository userRepository, BlockRecordRepository blockRecordRepository) {
        this.userRepository = userRepository;
        this.blockRecordRepository = blockRecordRepository;
    }

    public void blockUser(Long userId, User handler) throws ResourceNotFoundByIdException {
        User user = this.userRepository.findOne(userId);
        EntityUtil.requireEntityNotNull(user);
        this.blockUser(user, handler);
    }


    private void blockUser(User user, User handler) {
        BlockRecord blockRecord = new BlockRecord(user, handler);
        this.blockRecordRepository.save(blockRecord);
    }

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
