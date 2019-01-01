package io.jkratz.katas.socket.udp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPTest {

    private UDPServer server;
    private UDPClient client;

    @Before
    public void setup() throws UnknownHostException, SocketException {
        Thread thread = new Thread(new UDPServer());
        thread.start();
        this.client = new UDPClient(InetAddress.getByName("localhost"), 5000);
    }

    @After
    public void tearDown() {
        this.client.close();
        this.client = null;
    }

    @Test
    public void testSendMessage() throws IOException {
        String response = this.client.sendMessage("Hello");
        Assert.assertEquals("You said Hello", response);
    }
}
