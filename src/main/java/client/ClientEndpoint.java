package client;


import coders.MessageDecoder;
import coders.MessageEncoder;
import entities.Message;

import javax.websocket.*;


@javax.websocket.ClientEndpoint(decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class ClientEndpoint {

    @OnOpen
    public void onOpen(Session session) {
//    System.out.println("Connected to endpoint: " + session.getBasicRemote());


    }

    @OnMessage
    public void onMessage(Session session, Message msg) {
        System.out.println(msg.getRole() + "| " + msg.getName() + ": " + msg.getText());

    }

    @OnError
    public void processError(Throwable t) {
        t.printStackTrace();
    }
}
