package com.masterandroid.whatsapp.UserModel;

public class Messages {

    String UID,message;
    Long timeStramp;

    public Messages(String UID, String message, Long timeStramp) {
        this.UID = UID;
        this.message = message;
        this.timeStramp = timeStramp;
    }

    public Messages(String UID, String message) {
        this.UID = UID;
        this.message = message;
    }

    public Messages() {
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeStramp() {
        return timeStramp;
    }

    public void setTimeStramp(Long timeStramp) {
        this.timeStramp = timeStramp;
    }
}
