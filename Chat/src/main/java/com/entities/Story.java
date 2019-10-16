package com.entities;

import com.functional.MainCommands;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Story {
    private MainCommands command = new MainCommands();
    private static LinkedHashMap<Object, LinkedList<String>> storyMap = new LinkedHashMap<>();

    public void addStory(String el, Object session1) {
        storyMap.computeIfAbsent(session1, k -> new LinkedList<>()).add(el);

    }

    public void printStory(Object session1, Object session2, Message msg) {

        if (storyMap.size() > 0 && storyMap.containsKey(session1)) {
                for (String vr : storyMap.get(session1)) {
                    msg.setText(vr);
                    command.sendMsg(session1,msg);
                }
            storyMap.remove(session1);

        }

    }
}
