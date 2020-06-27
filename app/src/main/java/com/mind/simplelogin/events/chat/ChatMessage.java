package com.mind.simplelogin.events.chat;

import java.util.Date;

public class ChatMessage {

    private String messageText;
    private String messageUser;
    private long messageTime;

    public ChatMessage(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageTime = new Date().getTime();
    }

    public ChatMessage(String messageText, String messageUser, long time) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageTime = time;
    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageTime() {
        return String.valueOf(messageTime);
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}

