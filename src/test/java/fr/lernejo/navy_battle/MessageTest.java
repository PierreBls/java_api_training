package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class MessageTest {
    @Test
    void Test_message() throws IOException, InterruptedException {
        Message message = new Message("800", "myurl", "my message");
        Assertions.assertEquals("my message", message.getMessage());
    }

    @Test
    void Test_id() throws IOException, InterruptedException {
        Message message = new Message("800", "myurl", "my message");
        Assertions.assertEquals("800", message.getId());
    }

    @Test
    void Test_url() throws IOException, InterruptedException {
        Message message = new Message("800", "myurl", "my message");
        Assertions.assertEquals("myurl", message.getUrl());
    }
}
