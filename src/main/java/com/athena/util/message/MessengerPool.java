package com.athena.util.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Tommy on 2017/11/22.
 * <p>
 * Use to manage Messenger to send info.
 */
@Component
public class MessengerPool {
    private int messengerCount;

    public MessengerPool(@Value("${message.messenger.count}") int messengerCount) {
        this.messengerCount = messengerCount;

    }

    public Messenger instantiateMessenger(){

        return null;
    }
}
