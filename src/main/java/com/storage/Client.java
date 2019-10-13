package com.storage;

import com.entities.ConnectionType;

import javax.websocket.Session;

public class Client extends User{
    public Client(Session session, String name, ConnectionType type) {

        super(session, name,type);

    }


}
