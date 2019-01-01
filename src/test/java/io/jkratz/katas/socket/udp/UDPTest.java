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
        this.server = new UDPServer(5000);
        Thread thread = new Thread(server);
        thread.start();
        this.client = new UDPClient(InetAddress.getByName("localhost"), 5000);
    }

    @After
    public void tearDown() {
        this.client.close();
        this.client = null;
        this.server.stop();
        this.server = null;
    }

    @Test
    public void testSendMessage() throws IOException {
        String response = this.client.sendMessage("Hello");
        System.out.println(String.format("Response from server: %s", response));
        Assert.assertEquals("You said Hello", response);
    }
}
