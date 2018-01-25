package com.athena.util.messager.email;

import com.athena.model.message.AbstractUserGroup;
import com.athena.model.message.Message;
import com.athena.model.security.User;
import com.athena.util.messager.Messenger;
import org.springframework.stereotype.Component;

/**
 * Created by Tommy on 2017/12/19.
 */
@Component("Email")
public class EmailMessenger implements Messenger {

    @Override
    public void broadcast(AbstractUserGroup userGroup, Message message) {

    }

    @Override
    public void send(User receiver, Message message) {

    }
}
