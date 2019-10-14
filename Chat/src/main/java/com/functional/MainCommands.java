package com.functional;


import com.entities.ConnectionType;
import com.entities.Message;
import com.entities.Story;
import com.storage.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainCommands {
    private static final Logger log = LogManager.getLogger(MainCommands.class);

    private static SessionList storage = SessionList.getInstance();
    public static HashMap<Object, User> allUsers = new HashMap<>();
    private Object agentSession=null;
    private Agent agent;
    private Client client;
    private Story story;
    public static LinkedHashMap<Object,Object> chatMap = new LinkedHashMap<>();
    public void registerAgent(Agent agent)  {
        allUsers.put(agent.getSession(),agent);
        this.agent=agent;
        storage.addAgent(agent);
        storage.addAvailableAgent(agent);
        log.info("Agent registered :"+agent.getName()+" | "+agent.getType());
    }
    public void registerClient(Client client){
        allUsers.put(client.getSession(),client);
        this.client=client;
        storage.addWaitingClient(client);
        storage.addClient(client);
        log.info("Client registered :"+client.getName()+" | "+client.getType());
    }
    public void exit(Object session, Message msg){


        if (msg.getRole().equals("client") && chatMap.containsKey(this.agentSession)) {
            msg.setText("Disconnected");
            sendMsg(session,msg);
            chatMap.remove(agentSession);
            SessionList.sessionListAvailableAgents.add(agent);
            Collections.shuffle(SessionList.sessionListAvailableAgents);
            log.info("Client :"+msg.getName()+" /exit: ");
        } else if (msg.getRole().equals("agent") && chatMap.containsKey(session)) {
            msg.setText("Disconnected");
            sendMsg(session,msg);
            chatMap.remove(session);
            SessionList.getAllAgents().remove(agent.getUserId());
          log.info("Agent :"+msg.getName()+" /exit : ");
        }

        if(msg.getRole().equals("client")) {
            log.info("Client :" + msg.getName() + " /exit: ");
        }
        else if(msg.getRole().equals("agent"))
        {
            log.info("Agent :"+msg.getName()+" /exit : ");
        }

    }
    public void logIn(Object session,Message msg){
        tryToFindAgent(session,msg);

        if (chatMap.get(this.agentSession) != session) {
            if (!msg.getText().equals("Connected") && !msg.getText().equals("")) {

                story.addStory(msg.getText(), this.client.getSession());
                msg.setName("");
                msg.setRole("");
                msg.setText("Agent not connected");

                    User user=allUsers.get(this.client.getSession());
                    sendMsg(user,msg);

            }
        } else {
                if (!msg.getText().equals("")) {
                    sendMsg(session,msg);
                }
        }
    }
    public void sendMsg(Object session,Message msg){
        tryToFindAgent(session,msg);
        Object session1=null;
        if(chatMap.containsKey(session)){
            session1=chatMap.get(session);
        } else if (chatMap.containsValue(session)) {
            session1=this.agentSession;
        }
        User user=allUsers.get(session1);
        if(user.getType().equals(ConnectionType.HTTP)){
            storage.addRestMessages(msg,session1);
        }
        else {
            user.sendMessage(msg);
        }
    }

    public boolean leave(Object session, Message msg){
        boolean leave = true;
        if (msg.getRole().equals("client") && msg.getText().equals("/leave") && chatMap.containsKey(this.agentSession)) {
                msg.setText("Disconnected");
                sendMsg(session,msg);
           log.info("Client :"+msg.getName()+" disconnected");
            chatMap.remove(this.agentSession);
            SessionList.getAllClients().remove(client.getUserId());
            SessionList.sessionListAvailableAgents.add(this.agent);
            Collections.shuffle(SessionList.sessionListAvailableAgents);
            leave = false;
            if(SessionList.getAllAvailableAgents().isEmpty()){
                storage.addWaitingClient(client);
            }
        } else if (msg.getRole().equals("client") && msg.getText().equals("/leave") && !chatMap.containsKey(this.agentSession)) {
                msg.setName("");
                msg.setText("Agent not connected");
                sendMsg(session,msg);
                leave = false;
        }
        return leave;
    }
    private void tryToFindAgent(Object session, Message msg){
        Collections.shuffle(SessionList.sessionListAvailableAgents);
        if (msg.getRole().equals("client")&&!SessionList.getAllAvailableAgents().isEmpty() && !chatMap.containsKey(this.agentSession)) {
            this.agentSession = SessionList.getAllAvailableAgents().get(0).getSession();
            this.agent=SessionList.getAllAvailableAgents().get(0);
            chatMap.put(this.agentSession, session);
            Chat chat = new Chat(SessionList.getAllAvailableAgents().get(0), client);
            storage.addChat(chat);
            SessionList.getAllWaitingClients().remove(client);
            String msg1 = msg.getText();
            msg.setText("Connected");
            log.info(this.agent.getName()+" connected to client: "+msg.getName());
            sendMsg(session,msg);
            story=new Story();
            story.printStory(client.getSession(), this.agent.getSession(), msg);
            msg.setText(msg1);
            SessionList.getAllAvailableAgents().remove(0);
        }

    }

}
