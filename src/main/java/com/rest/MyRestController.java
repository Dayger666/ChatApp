package com.rest;

import com.infoForRest.AllInfoAboutChat;
import com.infoForRest.AllInfoAboutUser;
import com.infoForRest.InfoAboutUser;
import com.infoForRest.InfoAboutChat;
import com.storage.Chat;
import com.storage.Client;
import com.storage.SessionList;
import com.storage.Agent;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api")
public class MyRestController {
    private SessionList ses = SessionList.getInstance();

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
        Integer number=0;
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



}
