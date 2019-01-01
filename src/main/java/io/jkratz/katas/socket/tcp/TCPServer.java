package io.jkratz.katas.socket.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    private ServerSocket serverSocket;
    private final int port;

    public TCPServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        while (true) {
            new Thread(new ClientHandler(this.serverSocket.accept())).start();
        }
    }

    public void stop() throws IOException {
        this.serverSocket.close();
    }

    private static class ClientHandler implements Runnable {

        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            PrintWriter printWriter;
            BufferedReader reader;

            try {
                printWriter = new PrintWriter(this.socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                String input;
                while ((input = reader.readLine()) != null) {
                    printWriter.println("You said " + input);
                }

                printWriter.close();
                reader.close();
                this.socket.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
