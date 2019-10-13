package com.rest;

import com.entities.Message;
import com.functional.MainCommands;
import com.infoForRest.AllInfoAboutChat;
import com.infoForRest.AllInfoAboutUser;
import com.infoForRest.InfoAboutUser;
import com.infoForRest.InfoAboutChat;
import com.storage.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api")
public class MyRestController {
    private MainCommands command = new MainCommands();
    private String name;
    private String role;

    @RequestMapping(value = "/allAgents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<ArrayList<InfoAboutUser>> getAllAgents() {

        ArrayList<InfoAboutUser> result = new ArrayList<>();
        for (Agent agent : SessionList.getAllAgents().values()) {
            result.add(new InfoAboutUser(agent));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/infoAboutAgent/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<AllInfoAboutUser> getInfoAboutAgent(@PathVariable("id") long id) {

        Agent agent = SessionList.getAllAgents().get(id);

        if (agent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new AllInfoAboutUser(agent), HttpStatus.OK);

    }

    @RequestMapping(value = "/allAvailableAgents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<ArrayList<InfoAboutUser>> getAllAvailableAgents() {
        ArrayList<InfoAboutUser> result = new ArrayList<>();
        for (Agent agent : SessionList.getAllAvailabelAgents()) {
            result.add(new InfoAboutUser(agent));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/numberOfAvailableAgents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<Integer> getNumberOfAvailableAgents() {
        Integer number = 0;
        for (Agent agent : SessionList.getAllAvailabelAgents()) {
            number++;
        }

        return new ResponseEntity<>(number, HttpStatus.OK);

    }

    @RequestMapping(value = "/allWaitingClients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<ArrayList<InfoAboutUser>> getAllWaitingClients() {
        ArrayList<InfoAboutUser> result = new ArrayList<>();
        for (Client client : SessionList.getAllWaitingClients()) {
            result.add(new InfoAboutUser(client));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/allClients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<ArrayList<InfoAboutUser>> getAllClients() {
        ArrayList<InfoAboutUser> result = new ArrayList<>();
        for (Client client : SessionList.getAllClients().values()) {
            result.add(new InfoAboutUser(client));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/infoAboutClient/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<AllInfoAboutUser> getInfoAboutClient(@PathVariable("id") long id) {

        Client client = SessionList.getAllClients().get(id);

        if (client == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new AllInfoAboutUser(client), HttpStatus.OK);

    }

    @RequestMapping(value = "/allChats", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<ArrayList<InfoAboutChat>> getAllChats() {

        ArrayList<InfoAboutChat> result = new ArrayList<>();
        for (Chat chat : SessionList.getAllChats().values()) {
            result.add(new InfoAboutChat(chat));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/infoAboutChat/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<AllInfoAboutChat> getInfoAboutChat(@PathVariable("id") long id) {

        Chat chat = SessionList.getAllChats().get(id);

        if (chat == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new AllInfoAboutChat(chat), HttpStatus.OK);

    }

    @RequestMapping(value = "/registerAgent/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity regAgent(@PathVariable("name") String name, HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (MainCommands.allUsers.keySet().contains(session)) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        HttpAgent agent = new HttpAgent(request.getSession(), name);
        this.role = "agent";
        this.name = name;

        command.registerAgent(agent);

//        this.name = name;

        return new ResponseEntity(HttpStatus.OK);

    }


    @RequestMapping(value = "/registerClient/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity regClient(@PathVariable("name") String name, HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (MainCommands.allUsers.keySet().contains(session)) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        HttpClient client = new HttpClient(request.getSession(), name);

        command.registerClient(client);
        this.role = "client";
        this.name = name;

        return new ResponseEntity(HttpStatus.OK);

    }


    @RequestMapping(value = "/sendMessage/{text}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity sendMessage(@PathVariable("text") String text, HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (!MainCommands.allUsers.keySet().contains(session)) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Message msg = new Message(name, role, text);

        command.sendMsg(session, msg);

        return new ResponseEntity(HttpStatus.OK);

    }


//    @RequestMapping(value = "/messages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//
//    public ResponseEntity<ArrayList<Message>> getMessages(HttpServletRequest request) {
//
//        HttpSession session = request.getSession();
//
//        if (!storage.getAllUsers().keySet().contains(session)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//
//        HttpUser httpUser = (HttpUser) storage.getAllUsers().get(session);
//
//        return new ResponseEntity<>(httpUser.getMessages(), HttpStatus.OK);
//
//    }


    @RequestMapping(value = "/leave", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity leaveChat(HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (!MainCommands.allUsers.keySet().contains(session)) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Message msg = new Message(name, role, "/leave");
        command.leave(session, msg);


        return new ResponseEntity(HttpStatus.OK);

    }


    @RequestMapping(value = "/exit", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity exitChat(HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (!MainCommands.allUsers.keySet().contains(session)) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Message msg = new Message(name, role, "/exit");
        command.exit(session, msg);


        return new ResponseEntity(HttpStatus.OK);

    }


}