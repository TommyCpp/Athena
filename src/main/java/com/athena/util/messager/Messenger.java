package com.athena.util.messager;

import com.athena.model.message.AbstractUserGroup;
import com.athena.model.message.Message;
import com.athena.model.security.User;

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
