package com.infoForRest;

import com.storage.User;

import java.util.Date;

public class InfoAboutUser {

    private final long id;

    private final String name;



    public InfoAboutUser(long id, String name, Date regTime) {
        this.id = id;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public InfoAboutUser(User user) {

        this.id = user.getUserId();

        this.name = user.getName();

    }

}