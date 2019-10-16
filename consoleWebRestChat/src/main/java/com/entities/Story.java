package com.entities;


import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Story {
    private static LinkedHashMap<Object, LinkedList<String>> storyMap = new LinkedHashMap<>();

    public void addStory(String el, Object session1) {
        storyMap.computeIfAbsent(session1, k -> new LinkedList<>()).add(el);

    }

    public Message printStory(Object session1, Message msg) {

        if (storyMap.size() > 0 && storyMap.containsKey(session1)) {
                for (String vr : storyMap.get(session1)) {
                    msg.setText(vr);
                }
            storyMap.remove(session1);

        }
        return msg;
    }
}
