package com.storage;

import com.functional.Id;

import java.time.Instant;

public class Chat {
    private Instant starts = Instant.now();
    private long chatId;

    private final String nameClient;
    private final String nameAgent;
    private final Instant regTime;
    public Chat(Agent agent, Client client){
        this.nameAgent=agent.getName();
        this.nameClient=client.getName();
        this.regTime= starts;
        this.chatId= Id.getInstance().getChatId();

    }

    public long getChatId() {
        return chatId;
    }

    public String getNameClient() {
        return nameClient;
    }

    public String getNameAgent() {
        return nameAgent;
    }

    public Instant getStarts() {
        return starts;
    }

    public Instant getRegTime() {
        return regTime;
    }
}
