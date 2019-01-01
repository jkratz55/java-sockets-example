package io.jkratz.katas.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

public class UDPServer implements Runnable {

    private final AtomicBoolean running = new AtomicBoolean(false);
    private final DatagramSocket socket;

    public UDPServer(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
    }

    @Override
    public void run() {

        // Flag as running
        this.running.set(true);

        // While running wait for message from client and respond
        while (this.running.get()) {

            try {
                // Create buffer and packet to receive data
                byte[] buffer = new byte[256];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Waits for a packet to come in and fills the buffer with the data, THIS IS BLOCKING!
                this.socket.receive(packet);

                // Get the ip address and port of the client
                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                // Get the data received as a String
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received);

                // Build response packet and send it back to the client
                String response = "You said " + received;
                DatagramPacket responsePacket = new DatagramPacket(response.getBytes(), response.getBytes().length, address, port);
                socket.send(responsePacket);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // If we reach this point server is no longer running, close the socket
        this.socket.close();
    }

    public void stop() {
        this.running.set(false);
    }
}
