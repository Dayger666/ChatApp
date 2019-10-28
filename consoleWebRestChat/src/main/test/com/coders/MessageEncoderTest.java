package com.coders;


import com.entities.ConnectionType;
import com.google.gson.Gson;
import com.entities.Message;
import org.junit.Test;

import javax.websocket.EncodeException;

import static org.junit.Assert.assertEquals;


public class MessageEncoderTest {
    private static Gson gson = new Gson();
    @Test
    public void encode() throws EncodeException {
        Message msg=new Message("qwerty","agent","qq", ConnectionType.HTTP);
        MessageEncoder obj1=new MessageEncoder();
        assertEquals(gson.toJson(msg),obj1.encode(msg));
    }
}