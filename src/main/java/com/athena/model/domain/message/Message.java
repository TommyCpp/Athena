package com.athena.model.domain.message;

import com.athena.model.User;

/**
 * Created by Tommy on 2017/12/24.
 */
public interface Message {
    String getTitle();

    String getContent();

    User getSender();

}
