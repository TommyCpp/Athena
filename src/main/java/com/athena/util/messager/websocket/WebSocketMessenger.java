package com.athena.util.messager.websocket;

import com.athena.model.AbstractMessage;
import com.athena.model.User;
import com.athena.util.messager.Messenger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

/**
 * Created by Tommy on 2017/11/22.
 *
 * Use to send message via internal chat(WebSocket).
 */
@Component
public class WebSocketMessenger extends TextWebSocketHandler implements Messenger {

    @Override
    public void broadcast(List<User> receiver, AbstractMessage message) {

    }

    @Override
    public void send(User receiver, AbstractMessage message) {

    }
}
