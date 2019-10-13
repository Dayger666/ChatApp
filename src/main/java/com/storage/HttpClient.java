package com.storage;

import com.entities.ConnectionType;

import javax.servlet.http.HttpSession;

public class HttpClient extends Client {
    private HttpSession session;
    public HttpClient(HttpSession session, String name, ConnectionType type) {
        super(null,name,type);
        this.session=session;
    }
    @Override

    public Object getSession() {

        return session;

    }

}
