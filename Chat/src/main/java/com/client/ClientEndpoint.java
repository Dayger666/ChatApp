package com.client;


import com.coders.MessageDecoder;
import com.coders.MessageEncoder;
import com.entities.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.websocket.*;


@javax.websocket.ClientEndpoint(decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class ClientEndpoint {
    private static final Logger log = LogManager.getLogger(ClientEndpoint.class);

    @OnOpen
    public void onOpen(Session session) {


    }

    @OnMessage
    public void onMessage(Session session, Message msg) {
        System.out.println(msg.getRole() + "| " + msg.getName() + ": " + msg.getText());

    }

    @OnError
    public void processError(Throwable t) {
        log.error("Error : "+t);
    }
}
