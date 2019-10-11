package com.application;

import com.storage.SessionList;
import com.storage.Agent;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(value="/api")
public class MyRestController {
    private SessionList ses=SessionList.getInstance();
    @RequestMapping(value = "/allAgents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<ArrayList<DTSUser>> getAllAgents() {
        ArrayList<DTSUser> result = new ArrayList<>();
        for (Agent agent : SessionList.getAllAgents().values()) {
            System.out.println(new DTSUser(agent).getRegTime().toString());
            result.add(new DTSUser(agent));
            System.out.println(result);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);

    }


}
