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

        this.running.set(true);

        while (this.running.get()) {
            try (DatagramSocket socket = new DatagramSocket(5000)) {

                // Create buffer and packet to receive data
                byte[] buffer = new byte[256];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Waits for a packet to come in and fills the buffer with the data, THIS IS BLOCKING!
                socket.receive(packet);

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
                // If something should go wrong let's just print the exception
                System.out.println(ex);
            }
        }


    }

    public void stop() {
        this.running.set(false);
    }
}
