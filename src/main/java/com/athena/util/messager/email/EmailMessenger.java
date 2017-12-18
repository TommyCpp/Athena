package com.athena.util.messager.email;

import com.athena.model.User;
import com.athena.model.domain.message.AbstractMessage;
import com.athena.model.domain.message.AbstractUserGroup;
import com.athena.util.messager.Messenger;

/**
 * Created by Tommy on 2017/12/19.
 */
public class EmailMessenger implements Messenger {

    @Override
    public void broadcast(AbstractUserGroup userGroup, AbstractMessage message) {

    }

    @Override
    public void send(User receiver, AbstractMessage message) {

    }
}
