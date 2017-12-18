package com.athena.service.message;

import com.athena.model.domain.message.AbstractMessage;
import com.athena.model.domain.message.AuthorityUserGroup;
import com.athena.util.messager.Messenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * Created by Tommy on 2017/11/22.
 */
@Service
@DependsOn({"WebSocket"}) //list all implement of Messenger
public class SystemMessageService implements MessageService {

    private SimpleGrantedAuthority receiverAuthority;
    private Messenger messenger;

    @Autowired
    public SystemMessageService(ApplicationContext applicationContext, @Value("${message.system.method}") String messengerClass, @Value("${message.system.receiver}") String receiverAuthority) {
        this.messenger = (Messenger) applicationContext.getBean(messengerClass);
        this.receiverAuthority = new SimpleGrantedAuthority(receiverAuthority);
    }


    @Override
    public void send(AbstractMessage message) {
        this.messenger.broadcast(new AuthorityUserGroup(this.receiverAuthority), message);
    }

}
