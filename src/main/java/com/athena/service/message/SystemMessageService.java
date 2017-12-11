package com.athena.service.message;

import com.athena.model.AbstractMessage;
import com.athena.model.User;
import com.athena.util.messager.Messenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Tommy on 2017/11/22.
 */
@Service
public class SystemMessageService implements MessageService {

    private SimpleGrantedAuthority receiverAuthority;
    private List<User> receiver;
    private Messenger messenger;

    @Autowired
    public SystemMessageService(ApplicationContext applicationContext, @Value("${message.system.method}") String messengerClass, @Value("${message.system.receiver}") String receiverAuthority) {
        this.messenger = (Messenger) applicationContext.getBean(messengerClass);
        this.receiverAuthority = new SimpleGrantedAuthority(receiverAuthority);
    }

    @PostConstruct
    public void initialize(){
        this.receiver = this.findAllReceiver();
    }

    @Override
    public void send(AbstractMessage message) {
        this.messenger.broadcast(receiver, message);
    }

    private List<User> findAllReceiver() {
        //todo: find and cache the Receiver, when user with receiverAuthority is change, refresh the cache via AOP.
        return null;
    }
}
