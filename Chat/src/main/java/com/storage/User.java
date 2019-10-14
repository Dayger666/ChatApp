package com.storage;

import com.functional.IdGenerator;
import com.entities.ConnectionType;
import com.entities.Message;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;

public abstract class User {
    private long userId;

    private String name;
    private Date regTime;
    private Session session;
    private ConnectionType type;


    User(Session session, String name, ConnectionType type) {
        this.session=session;
        this.name = name;
        this.regTime = new Date();
        this.userId= IdGenerator.getInstance().getUserId();
        this.type=type;
    }

    User(String name) {
        this.name=name;
        this.regTime = new Date();
        this.userId= IdGenerator.getInstance().getUserId();
    }

User(){

}


    public long getUserId() {
        return userId;
    }

    public Object getSession() {

        return session;

    }
    public void setSession(Session session) {

        this.session = session;

    }


    public String getName() {

        return name;

    }

    public Date getRegTime() {

        return regTime;

    }
    public void sendMessage(Message msg){
        try {
            session.getBasicRemote().sendObject(msg);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }


    public ConnectionType getType() {
        return type;
    }

    public void setType(ConnectionType type) {
        this.type = type;
    }
}