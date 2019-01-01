package io.jkratz.katas.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {

    private final DatagramSocket socket;
    private final InetAddress address;
    private final int port;

    public UDPClient(InetAddress address, int port) throws SocketException {
        this.address = address;
        this.port = port;
        this.socket = new DatagramSocket();
    }

    public String sendMessage(String message) throws IOException {

        // Convert message to byte array and send to server
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);

        // Setup packet for receiving response
        byte[] responseBuffer = new byte[256];
        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
        socket.receive(responsePacket);
        return new String(responsePacket.getData(), 0, responsePacket.getLength());
    }

    public void close() {
        socket.close();
    }
}
