package com.client;

import com.entities.ConnectionType;
import com.entities.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.websocket.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;


//mvn exec:java -Dexec.mainClass=client.Client
public class Client {
    private static final Logger log = LogManager.getLogger(Client.class);
    private static Session session;
    private WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    private BufferedReader inputUser;
    private static String name;
    private static String role;
    private static final String SERVER = "ws://localhost:8080/webChatApp_war/chat";

    private void Registration() {
        System.out.print("Registration: ");
        try {
            while (true) {
                inputUser = new BufferedReader(new InputStreamReader(System.in));
                String register = inputUser.readLine();
                if (register.startsWith("/register agent ") || register.startsWith("/register client ")) {
                    String[] register1 = register.split(" ");
                    if (register1.length == 3) {

                        role = register1[1];
                        name = register1[2];
                        session = container.connectToServer(ClientEndpoint.class, URI.create(SERVER));
                        Message msg = new Message(name, role, "Connected", ConnectionType.WEBSOCKET);
                        session.getBasicRemote().sendObject(msg);

                        break;
                    } else {
                        System.out.println("Incorrect specified parameters. /register (agent/client) (nickname)");
                    }
                } else {
                    System.out.println("Incorrect input");
                }
            }
        } catch (IOException ignored) {
        } catch (DeploymentException | EncodeException e) {
           log.error("Error : "+e);
        }
    }


    public static void main(String[] args) {
        String userWord;
        Client client = new Client();
        client.Registration();
        while (true) {
            try {
                userWord = client.inputUser.readLine();
                if(!userWord.equals("")) {
                    Message msg = new Message(name, role, userWord,ConnectionType.WEBSOCKET);
                    session.getBasicRemote().sendObject(msg);
                }
                if (userWord.equals("/exit")) {
                    break;
                } else if (userWord.equals("/leave")) {
                    System.out.println("You left,please send a message to reconnect");
                } else {
                    System.out.println("Me: " + userWord);
                }
            } catch (IOException | EncodeException e) {
                log.error("Exception "+e);
            }
        }
    }
}
