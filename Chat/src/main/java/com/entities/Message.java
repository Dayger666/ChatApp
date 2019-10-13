package com.entities;

import lombok.Data;

@Data
public class Message {
//    public Message(String name, String role, String text,MessageType type) {
//        this.name = name;
//        this.role = role;
//        this.text = text;
//        this.type=type;
//    }
    public Message(String name, String role, String text,ConnectionType type) {
        this.name = name;
        this.role = role;
        this.text = text;
        this.type=type;
    }

    private String name;
    private String role;
    private String text;
    private ConnectionType type;
//    private MessageType type;


    public void setText(String text) {
        this.text = text;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }


    public ConnectionType getType() {
        return type;
    }

    public void setType(ConnectionType type) {
        this.type = type;
    }
}

