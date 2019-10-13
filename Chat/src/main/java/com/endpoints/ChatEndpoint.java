package com.endpoints;

import com.coders.MessageDecoder;
import com.coders.MessageEncoder;
import com.entities.ConnectionType;
import com.entities.Message;
import com.entities.Story;
import com.functional.MainCommands;
import com.storage.Agent;
import com.storage.Chat;
import com.storage.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.storage.SessionList;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.LinkedHashMap;
import java.util.LinkedList;


@ServerEndpoint(value = "/chat", decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class ChatEndpoint {
    private static final Logger log = LogManager.getLogger(ChatEndpoint.class);
    private MainCommands command = new MainCommands();
    private Story story = new Story();
    private Session agentSession = null;
    private Agent agent=new Agent();
    private Client client=null;
    private Chat chat;
    private static SessionList storage = SessionList.getInstance();
    private static LinkedList<Session> sessionList = new LinkedList<>();
    private static LinkedList<Session> sessionListAvailableAgents = new LinkedList<>();
    private static LinkedHashMap<Session, Session> chatMap = new LinkedHashMap<>();


    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session);
        log.info("Session opened :"+session);
        sessionList.add(session);

    }

    @OnClose
    public void onClose(Session session) {
        sessionList.remove(session);

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("Error :"+throwable);
        sessionList.remove(session);
        sessionListAvailableAgents.remove(session);
        chatMap.remove(session);
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, Message msg) {
        System.out.println(session);
        if (msg.getRole().equals("agent") && !SessionList.getAllAvailableAgents().contains(agent) && !MainCommands.chatMap.containsKey(session)) {
            System.out.println(agent.getSession());
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

//    private void exit(Session session, Message msg) {
//
//        if (msg.getRole().equals("client") && chatMap.containsKey(this.agentSession)) {
//            msg.setText("Disconnected");
//            try {
//                this.agentSession.getBasicRemote().sendObject(msg);
//            } catch (IOException | EncodeException e) {
//                e.printStackTrace();
//            }
//            chatMap.remove(agentSession);
//            sessionListAvailableAgents.add(agentSession);
//            Collections.shuffle(sessionListAvailableAgents);
//            log.info("Client :"+msg.getName()+" /exit: ");
//        } else if (msg.getRole().equals("agent") && chatMap.containsKey(session)) {
//            msg.setText("Disconnected");
//            try {
//                chatMap.get(session).getBasicRemote().sendObject(msg);
//            } catch (IOException | EncodeException e) {
//                e.printStackTrace();
//            }
//            chatMap.remove(session);
//            log.info("Agent :"+msg.getName()+" /exit : ");
//        }
//
//            if(msg.getRole().equals("client")) {
//                log.info("Client :" + msg.getName() + " /exit: ");
//            }
//            else if(msg.getRole().equals("agent"))
//            {
//                log.info("Agent :"+msg.getName()+" /exit : ");
//            }
//
//        onClose(session);
//    }
//    private void logIn(Session session,Message msg){
//        Collections.shuffle(SessionList.sessionListAvailableAgents);
//        if (sessionList.contains(session) && !SessionList.getAllAvailableAgents().isEmpty() && !chatMap.containsKey(this.agentSession)) {
//            this.agentSession = SessionList.getAllAvailableAgents().get(0).getSession();
//
//            chatMap.put(this.agentSession, session);
//            chat=new Chat(SessionList.getAllAvailableAgents().get(0),client);
//            storage.addChat(chat);
//            SessionList.getAllWaitingClients().remove(client);
//            String msg1 = msg.getText();
//            msg.setText("Connected");
//            log.info("Agent connected to client: "+msg.getName());
//            try {
//                this.agentSession.getBasicRemote().sendObject(msg);
//            } catch (IOException | EncodeException e) {
//                e.printStackTrace();
//            }
//            story.printStory(session, this.agentSession, msg);
//            msg.setText(msg1);
//            SessionList.getAllAvailableAgents().remove(0);
//        }
//        if (chatMap.get(this.agentSession) != session) {
//            if (!msg.getText().equals("Connected") && !msg.getText().equals("")) {
//
//                story.addStory(msg.getText(), session);
//                msg.setName("");
//                msg.setRole("");
//                msg.setText("Agent not connected");
//                try {
//                    session.getBasicRemote().sendObject(msg);
//                } catch (IOException | EncodeException e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            try {
//                if (!msg.getText().equals("")) {
//                    this.agentSession.getBasicRemote().sendObject(msg);
//                }
//            } catch (IOException | EncodeException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    private boolean leave(Session session,Message msg){
//        boolean leave = true;
//        if (msg.getRole().equals("client") && msg.getText().equals("/leave") && chatMap.containsKey(this.agentSession)) {
//            try {
//                msg.setText("Disconnected");
//                this.agentSession.getBasicRemote().sendObject(msg);
//            } catch (IOException | EncodeException e) {
//                e.printStackTrace();
//            }
//            log.info("Client :"+msg.getName()+" disconnected");
//            chatMap.remove(this.agentSession);
////            SessionList.getAllClients().remove()
//            sessionListAvailableAgents.add(this.agentSession);
//            Collections.shuffle(sessionListAvailableAgents);
//            leave = false;
//            if(SessionList.getAllAvailableAgents().isEmpty()){
//                storage.addWaitingClient(client);
//            }
//        } else if (msg.getRole().equals("client") && msg.getText().equals("/leave") && !chatMap.containsKey(this.agentSession)) {
//            try {
//                msg.setName("");
//                msg.setText("Agent not connected");
//                session.getBasicRemote().sendObject(msg);
//            } catch (IOException | EncodeException e) {
//                e.printStackTrace();
//            }
//            leave = false;
//        }
//        return leave;
//    }
}

