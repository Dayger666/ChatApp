package com.infoForRest;

import com.entities.Message;

public class RestMessages {

    private String name;
    private String text;
    public RestMessages(Message msg) {
        this.name=msg.getName();
        this.text=msg.getText();

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
