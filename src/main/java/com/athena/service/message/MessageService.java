package com.athena.service.message;

import com.athena.model.domain.message.AbstractMessage;

/**
 * Created by Tommy on 2017/11/18.
 */
public interface MessageService {
    void send(AbstractMessage message);
}
