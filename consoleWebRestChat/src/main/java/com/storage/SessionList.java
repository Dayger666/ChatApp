package com.storage;

import com.entities.Message;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class SessionList {


    private static SessionList instance;
    public static LinkedList<Agent> sessionListAvailableAgents = new LinkedList<>();
    private static LinkedList<Client> waitingListClients = new LinkedList<>();
    private static HashMap<Long, Agent> allAgents = new HashMap<>();
    private static HashMap<Long, Client> allClients = new HashMap<>();
    private static HashMap<Long, Chat> allChats = new HashMap<>();
    private static LinkedHashMap<Object, LinkedList<Message>> restMessages = new LinkedHashMap<>();

    public static synchronized SessionList getInstance() {
        if (instance == null) {
            instance = new SessionList();
        }
        return instance;
    }


    public void addAgent(Agent agent){
        allAgents.put(agent.getUserId(), agent);
    }
    public void addClient(Client client){
        allClients.put(client.getUserId(), client);
    }
    public void addAvailableAgent(Agent agent){
        sessionListAvailableAgents.add(agent);
    }
    public void addWaitingClient(Client client){
        waitingListClients.add(client);
    }
    public void addChat(Chat chat){
        allChats.put(chat.getChatId(),chat);
    }
    public void addRestMessages(Message el, Object session) {
        restMessages.computeIfAbsent(session, k -> new LinkedList<>()).add(el);

    }

    public static synchronized HashMap<Long, Agent> getAllAgents() {

        return allAgents;

    }
    public static synchronized HashMap<Long, Client> getAllClients() {

        return allClients;

    }
    public static synchronized LinkedList<Agent> getAllAvailableAgents() {

        return sessionListAvailableAgents;

    }
    public static synchronized LinkedList<Client> getAllWaitingClients() {

        return waitingListClients;

    }
    public static synchronized HashMap<Long, Chat> getAllChats() {

        return allChats;

    }
    public static synchronized LinkedHashMap<Object, LinkedList<Message>> getRestMessages() {
        return restMessages;

    }





}
