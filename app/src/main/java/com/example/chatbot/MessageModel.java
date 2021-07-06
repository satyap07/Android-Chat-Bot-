package com.example.chatbot;

import com.google.gson.annotations.SerializedName;

public class MessageModel {

    private String cnt;


    public MessageModel(String cnt) {
        this.cnt = cnt;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }
}
