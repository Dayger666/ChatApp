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
           log.info("Agent :"+msg.getName()+" /exit : ");
        }

        if(msg.getRole().equals("client")) {
            SessionList.getAllClients().remove(client.getUserId());
            SessionList.getAllWaitingClients().remove(client);
           log.info("Client :" + msg.getName() + " /exit: ");
        }
        else if(msg.getRole().equals("agent"))
        {
            SessionList.getAllAgents().remove(agent.getUserId());
            SessionList.getAllAvailableAgents().remove(agent);
            log.info("Agent :"+msg.getName()+" /exit : ");
        }

        allUsers.remove(session);

    }
    public void logIn(Object session,Message msg){
        tryToFindAgent(session,msg);

        if (chatMap.get(this.agentSession) != session) {
            if (!msg.getText().equals("Connected") && !msg.getText().equals("")) {
                story=new Story();
                story.addStory(msg.getText(), this.client.getSession());
                msg.setName("");
                msg.setRole("");
                msg.setText("Agent not connected");
                sendMsg(this.client.getSession(),msg);

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
        if (allUsers.get(session1)==null) {
       session1=session;
        }
            User user = allUsers.get(session1);
            if (user.getType() == (ConnectionType.HTTP)) {
                storage.addRestMessages(msg, session1);
            } else {
                user.sendMessage(msg);
            }

    }

    public boolean leave(Object session, Message msg){
        boolean leave = true;
        if (msg.getRole().equals("client") && msg.getText().equals("/leave") && chatMap.containsKey(this.agentSession)) {
                msg.setText("Disconnected");
                sendMsg(session,msg);
            log.info("Client :"+msg.getName()+" disconnected");
            System.out.println("this"+this.agentSession);
            chatMap.remove(this.agentSession);
            chatMap.remove(this.agent.getSession());
           // SessionList.getAllClients().remove()
            SessionList.sessionListAvailableAgents.add(this.agent);
            Collections.shuffle(SessionList.sessionListAvailableAgents);
            leave = false;
            if(SessionList.getAllAvailableAgents().isEmpty()){
                storage.addWaitingClient(client);
            }
        } else if (msg.getRole().equals("client") && msg.getText().equals("/leave") && !chatMap.containsKey(this.agentSession)) {
                leave = false;
        }
        return leave;
    }
    private void tryToFindAgent(Object session, Message msg){
        if (msg.getRole().equals("client")&&!SessionList.getAllAvailableAgents().isEmpty() && !chatMap.containsValue(session)) {
            Collections.shuffle(SessionList.sessionListAvailableAgents);
            this.agentSession = SessionList.getAllAvailableAgents().get(0).getSession();
            agentSession = SessionList.getAllAvailableAgents().get(0).getSession();
            this.agent=SessionList.getAllAvailableAgents().get(0);
            this.agent=SessionList.getAllAvailableAgents().get(0);
            chatMap.put(this.agentSession, session);
            Chat chat = new Chat(this.agent, this.client);
            storage.addChat(chat);
            SessionList.getAllWaitingClients().remove(client);
            String msg1 = msg.getText();
            msg.setText("Connected");
            log.info(this.agent.getName()+" connected to client : "+msg.getName());
            sendMsg(session,msg);
            story=new Story();
            sendMsg(session,story.printStory(session, msg));
            SessionList.getAllAvailableAgents().remove(0);
            msg.setText(msg1);
        }

    }

}
