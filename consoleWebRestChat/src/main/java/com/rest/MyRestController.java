package com.rest;

import com.entities.ConnectionType;
import com.entities.Message;
import com.functional.MainCommands;
import com.infoForRest.AllInfoAboutChat;
import com.infoForRest.AllInfoAboutUser;
import com.infoForRest.InfoAboutChat;
import com.infoForRest.InfoAboutUser;
import com.infoForRest.RestMessages;

import com.storage.Agent;
import com.storage.Chat;
import com.storage.Client;
import com.storage.HttpAgent;
import com.storage.HttpClient;
import com.storage.SessionList;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedList;

@RestController
@RequestMapping(value = "/api")
public class MyRestController {
    private MainCommands command = new MainCommands();
    private String name;
    private String role;

    @RequestMapping(value = "/allAgents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArrayList<InfoAboutUser>> getAllAgents(@RequestParam(name = "pageNumber",required = false,defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize) {

        ArrayList<InfoAboutUser> result = new ArrayList<>();
        ArrayList<InfoAboutUser> resultWithPagination = new ArrayList<>();
        for (Agent agent : SessionList.getAllAgents().values()) {
            result.add(new InfoAboutUser(agent));
        }
        for(int i=pageNumber*pageSize;i<pageNumber*pageSize+pageSize&&i<result.size();i++)
        {
            resultWithPagination.add(result.get(i));
        }
        return new ResponseEntity<>(resultWithPagination, HttpStatus.OK);

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
    public ResponseEntity<ArrayList<InfoAboutUser>> getAllAvailableAgents(@RequestParam(name = "pageNumber",required = false,defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize) {
        ArrayList<InfoAboutUser> result = new ArrayList<>();
        ArrayList<InfoAboutUser> resultWithPagination = new ArrayList<>();
        for (Agent agent : SessionList.getAllAvailableAgents()) {
            result.add(new InfoAboutUser(agent));
        }
        for(int i=pageNumber*pageSize;i<pageNumber*pageSize+pageSize&&i<result.size();i++)
        {
            resultWithPagination.add(result.get(i));
        }

        return new ResponseEntity<>(resultWithPagination, HttpStatus.OK);

    }

    @RequestMapping(value = "/numberOfAvailableAgents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Integer> getNumberOfAvailableAgents() {
        Integer number = 0;
        for (Agent agent : SessionList.getAllAvailableAgents()) {
            number++;
        }

        return new ResponseEntity<>(number, HttpStatus.OK);

    }

    @RequestMapping(value = "/allWaitingClients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArrayList<InfoAboutUser>> getAllWaitingClients(@RequestParam(name = "pageNumber",required = false,defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize) {
        ArrayList<InfoAboutUser> result = new ArrayList<>();
        ArrayList<InfoAboutUser> resultWithPagination = new ArrayList<>();
        for (Client client : SessionList.getAllWaitingClients()) {
            result.add(new InfoAboutUser(client));
        }
        for(int i=pageNumber*pageSize;i<pageNumber*pageSize+pageSize&&i<result.size();i++)
        {
            resultWithPagination.add(result.get(i));
        }
        return new ResponseEntity<>(resultWithPagination, HttpStatus.OK);

    }

    @RequestMapping(value = "/allClients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArrayList<InfoAboutUser>> getAllClients(@RequestParam(name = "pageNumber",required = false,defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize) {
        ArrayList<InfoAboutUser> result = new ArrayList<>();
        ArrayList<InfoAboutUser> resultWithPagination = new ArrayList<>();
        for (Client client : SessionList.getAllClients().values()) {
            result.add(new InfoAboutUser(client));
        }
        for(int i=pageNumber*pageSize;i<pageNumber*pageSize+pageSize&&i<result.size();i++)
        {
            resultWithPagination.add(result.get(i));
        }
        return new ResponseEntity<>(resultWithPagination, HttpStatus.OK);

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
    public ResponseEntity<ArrayList<InfoAboutChat>> getAllChats(@RequestParam(name = "pageNumber",required = false,defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize) {

        ArrayList<InfoAboutChat> result = new ArrayList<>();
        ArrayList<InfoAboutChat> resultWithPagination = new ArrayList<>();
        for (Chat chat : SessionList.getAllChats().values()) {
            result.add(new InfoAboutChat(chat));
        }
        for(int i=pageNumber*pageSize;i<pageNumber*pageSize+pageSize&&i<result.size();i++)
        {
            resultWithPagination.add(result.get(i));
        }

        return new ResponseEntity<>(resultWithPagination, HttpStatus.OK);

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

        HttpAgent agent = new HttpAgent(request.getSession(), name,ConnectionType.HTTP);
        this.role = "agent";
        this.name = name;

        command.registerAgent(agent);

        return new ResponseEntity(HttpStatus.OK);

    }


    @RequestMapping(value = "/registerClient/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity regClient(@PathVariable("name") String name, HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (MainCommands.allUsers.keySet().contains(session)) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        HttpClient client = new HttpClient(request.getSession(), name,ConnectionType.HTTP);

        command.registerClient(client);
        this.role = "client";
        this.name = name;

        return new ResponseEntity(HttpStatus.OK);

    }


    @RequestMapping(value = "/sendMessage/{text}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity sendMessage(@PathVariable("text") String text, HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (!MainCommands.allUsers.keySet().contains(session)) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Message msg = new Message(name, role, text,ConnectionType.HTTP);

        command.sendMsg(session, msg);

        return new ResponseEntity(HttpStatus.OK);

    }


    @RequestMapping(value = "/messages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LinkedList<RestMessages>> getMessages(HttpServletRequest request) {
        LinkedList<RestMessages> result=new LinkedList<>();
        HttpSession session = request.getSession();

        if (!MainCommands.allUsers.keySet().contains(session)){ return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        if (SessionList.getRestMessages().size() > 0 && SessionList.getRestMessages().containsKey(session)) {
            for (Message msg : SessionList.getRestMessages().get(session)) {
            result.add(new RestMessages(msg));
       }
            SessionList.getRestMessages().remove(session);

        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/leave", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity leaveChat(HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (!MainCommands.allUsers.keySet().contains(session)) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Message msg = new Message(name, role, "/leave",ConnectionType.HTTP);
        command.leave(session, msg);


        return new ResponseEntity(HttpStatus.OK);

    }


    @RequestMapping(value = "/exit", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity exitChat(HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (!MainCommands.allUsers.keySet().contains(session)) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Message msg = new Message(name, role, "/exit", ConnectionType.HTTP);
        command.exit(session, msg);


        return new ResponseEntity(HttpStatus.OK);

    }


}
