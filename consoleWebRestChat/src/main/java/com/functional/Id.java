package com.functional;

public class Id {
    private long userId = 0;
    private long chatId = 0;

    private static Id instance;


    public static synchronized Id getInstance(){
        if (instance == null){
            instance = new Id();
        }
        return instance;
    }


    private Id(){}

    public synchronized long getUserId(){
        return ++userId;
    }

    public synchronized long getChatId(){
        return ++chatId;
    }

}