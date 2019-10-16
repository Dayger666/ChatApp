package com.infoForRest;

import com.storage.Chat;

import java.time.Duration;
import java.time.Instant;

public class AllInfoAboutChat {



    private final long id;
    private final String nameClient;
    private final String nameAgent;
    private final long durationInMinutes;


    public long getId() {
        return id;
    }

    public AllInfoAboutChat(Chat chat) {
        this.id = chat.getChatId();
        this.nameClient = chat.getNameClient();
        this.nameAgent=chat.getNameAgent();
        Instant ends = Instant.now();
        this.durationInMinutes= (Duration.between(chat.getRegTime(), ends)).toMinutes();

    }

    public String getNameClient() {
        return nameClient;
    }

    public String getNameAgent() {
        return nameAgent;
    }


    public long getDurationInMinutes() {
        return durationInMinutes;
    }
}
