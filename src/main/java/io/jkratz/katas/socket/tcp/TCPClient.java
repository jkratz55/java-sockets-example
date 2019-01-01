package io.jkratz.katas.socket.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient  {

    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;

    public TCPClient(InetAddress address, int port) throws IOException {
        this.socket = new Socket(address, port);
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public String sendMessage(String message) throws IOException {
        out.println(message);
        return in.readLine();
    }

    public void disconnect() throws IOException {
        this.in.close();
        this.out.close();
        this.socket.close();
    }
}
