package com.endpoints;

import com.coders.MessageDecoder;
import com.coders.MessageEncoder;
import com.entities.ConnectionType;
import com.entities.Message;
import com.functional.MainCommands;
import com.storage.Agent;
import com.storage.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.storage.SessionList;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat", decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class ChatEndpoint {
    private static final Logger log = LogManager.getLogger(ChatEndpoint.class);
    private MainCommands command = new MainCommands();

    private Agent agent=new Agent();
    private Client client=null;


    @OnOpen
    public void onOpen(Session session) {
        log.info("Session opened :"+session);

    }

    @OnClose
    public void onClose(Session session) {
        log.info("Session closed: " +session);

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("Error :"+throwable+"Session : "+session);
    }

    @OnMessage
    public void onMessage(Session session, Message msg) {
        if (msg.getRole().equals("agent") && !SessionList.getAllAvailableAgents().contains(agent) && !MainCommands.chatMap.containsKey(session)) {
            agent = new Agent(session, msg.getName(), ConnectionType.WEBSOCKET);
            command.registerAgent(agent);
            log.info(msg.getRole()+"|"+msg.getName()+" registered");
        }
        else if(msg.getRole().equals("client")&& !SessionList.getAllWaitingClients().contains(client)&&MainCommands.chatMap.get(agent.getSession())!=session)
        {
            client=new Client(session,msg.getName(),ConnectionType.WEBSOCKET);
            command.registerClient(client);
        }
        if (msg.getText().equals("/exit")) {
            command.exit(session, msg);
        } else {
            if (msg.getRole().equals("agent") && MainCommands.chatMap.containsKey(session)) {
                command.sendMsg(session,msg);
            }
            if (msg.getRole().equals("client")&&command.leave(session,msg)) {
                command.logIn(session,msg);
            }
        }
    }

}

