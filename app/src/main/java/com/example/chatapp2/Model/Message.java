package com.example.chatapp2.Model;

public class Message {
    private String senderId,receiverId,message,messageId,type,imageUrlHD;
    private boolean isSeen;
    private long time;

    public Message() {
    }

    public Message(String senderId, String receiverId, String message, String messageId, String type, String imageUrlHD, boolean isSeen, long time) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.messageId = messageId;
        this.type = type;
        this.imageUrlHD = imageUrlHD;
        this.isSeen = isSeen;
        this.time = time;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrlHD() {
        return imageUrlHD;
    }

    public void setImageUrlHD(String imageUrlHD) {
        this.imageUrlHD = imageUrlHD;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
