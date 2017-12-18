package com.athena.model.domain.message;

import com.athena.model.User;

/**
 * Created by Tommy on 2017/11/18.
 * Abstract Message which can be email or SMS
 */
abstract public class AbstractMessage {
    protected String title;
    protected String content;
    protected User sender;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

}
