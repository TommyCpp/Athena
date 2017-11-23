package com.athena.repository.jpa;

import com.athena.model.Block;
import com.athena.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Tommy on 2017/11/23.
 */
public interface BlockRepository extends JpaRepository<Block, User> {
}
