package io.jkratz.katas.socket.tcp;

import org.junit.*;

import java.io.IOException;
import java.net.InetAddress;

public class TCPTest {

    private TCPServer server;
    private TCPClient client;

    @Before
    public void setup() throws IOException {
        this.server = new TCPServer(5000);
        this.server.start();

        this.client = new TCPClient(InetAddress.getByName("localhost"), 5000);
    }

    @After
    public void tearDown() throws IOException {
        this.client.disconnect();
        this.server.stop();

        this.client = null;
        this.server = null;
    }

    @Test
    public void testSendMessage() throws IOException {
        String response = this.client.sendMessage("I love Bekah!");
        System.out.println(response);
        Assert.assertEquals("You said I love Bekah!", response);
    }
}
