package com.athena.util.messager.websocket;

import com.athena.model.User;
import com.athena.model.domain.message.AbstractMessage;
import com.athena.model.domain.message.AbstractUserGroup;
import com.athena.repository.jpa.UserRepository;
import com.athena.util.messager.Messenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

/**
 * Created by Tommy on 2017/11/22.
 * <p>
 * Use to send message via internal chat(WebSocket).
 */
@Component("WebSocket")
public class WebSocketMessenger extends TextWebSocketHandler implements Messenger {

    private UserRepository userRepository;

    @Autowired
    public WebSocketMessenger(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public void broadcast(List<User> receiver, AbstractMessage message) {

    }

    @Override
    public void broadcast(AbstractUserGroup userGroup, AbstractMessage message) {
        List<User> receivers = this.userRepository.findAll(Example.of(userGroup.getExample(), userGroup.getExampleMatcher()));//todo: test
        this.broadcast(receivers, message);
    }

    @Override
    public void send(User receiver, AbstractMessage message) {

    }
}
