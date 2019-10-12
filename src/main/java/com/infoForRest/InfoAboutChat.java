package com.infoForRest;

import com.storage.Chat;

public class InfoAboutChat {


    private final long id;
    private final String nameClient;
    private final String nameAgent;





    public long getId() {
        return id;
    }

    public InfoAboutChat(Chat chat) {
        this.id = chat.getChatId();
        this.nameClient = chat.getNameClient();
        this.nameAgent=chat.getNameAgent();

    }

    public String getNameClient() {
        return nameClient;
    }

    public String getNameAgent() {
        return nameAgent;
    }
}
