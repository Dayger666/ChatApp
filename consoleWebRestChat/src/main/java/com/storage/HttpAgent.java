package com.storage;

import com.entities.ConnectionType;

import javax.servlet.http.HttpSession;


public class HttpAgent extends Agent {

    private HttpSession session;
    public HttpAgent(HttpSession session, String name, ConnectionType type) {
        super(null,name,type);
        this.session=session;
    }
    @Override

    public Object getSession() {

        return session;

    }
}
