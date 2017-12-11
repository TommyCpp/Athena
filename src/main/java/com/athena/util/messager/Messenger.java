package com.athena.util.messager;

import com.athena.model.AbstractMessage;
import com.athena.model.User;

import java.util.List;

/**
 * Created by Tommy on 2017/12/11.
 * <p>
 * define class to send and receive message
 */
public interface Messenger {
    default void broadcast(List<User> receiver, AbstractMessage message) {
        receiver.forEach(user -> this.send(user, message));
    }

    void send(User receiver, AbstractMessage message);
}
