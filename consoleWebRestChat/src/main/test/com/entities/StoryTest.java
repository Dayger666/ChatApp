package com.entities;

import org.junit.Before;
import org.junit.Test;
import org.mockito.session.MockitoSessionBuilder;

import javax.websocket.DeploymentException;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockitoSession;

public class StoryTest {
    private static Story story;

    @Before
    public  void setUp() {
        story=new Story();

    }

    @Test
    public void check_add_and_print_Story() throws IOException, DeploymentException {
        MockitoSessionBuilder session1 = mockitoSession();
        Message msg=mock(Message.class);
        String el="qwe";
        story.addStory(el, session1);
        System.out.println(story.printStory(session1,msg));
        assertNotNull(story.printStory(session1,msg));
    }
}