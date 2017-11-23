package com.athena.repository.jpa;

import com.athena.model.BlockedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Tommy on 2017/11/23.
 */
@Repository
public interface BlockedUserRepository extends JpaRepository<BlockedUser, Long> {
}
