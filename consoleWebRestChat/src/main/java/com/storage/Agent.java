package com.storage;

import com.entities.ConnectionType;

import javax.websocket.Session;


public class Agent extends User {

    public Agent(Session session, String name, ConnectionType type) {

        super(session, name,type);

    }


    public Agent() {
        super();
    }

}