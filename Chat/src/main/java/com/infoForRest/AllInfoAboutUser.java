package com.infoForRest;

import com.entities.ConnectionType;
import com.storage.User;

import java.util.Date;

public class AllInfoAboutUser {


    private final long id;

    private final String name;

    private final String regTime;
    private ConnectionType type;

    public AllInfoAboutUser(long id, String name, Date regTime) {
        this.id = id;
        this.name = name;
        this.regTime = regTime.toString();
    }

    public String getRegTime() {
        return regTime;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public AllInfoAboutUser(User user) {

        this.id = user.getUserId();

        this.name = user.getName();

        this.regTime = user.getRegTime().toString();
        this.type=user.getType();

    }

    public ConnectionType getType() {
        return type;
    }
}
