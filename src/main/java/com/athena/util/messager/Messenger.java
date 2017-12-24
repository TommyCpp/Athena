package com.athena.util.messager;

import com.athena.model.User;
import com.athena.model.domain.message.AbstractUserGroup;
import com.athena.model.domain.message.Message;

import java.util.List;

/**
 * Created by Tommy on 2017/12/11.
 * <p>
 * define class to send and receive message
 */
public interface Messenger {
    default void broadcast(List<User> receiver, Message message) {
        receiver.forEach(user -> this.send(user, message));
    }

    void broadcast(AbstractUserGroup userGroup, Message message);

    void send(User receiver, Message message);
}
