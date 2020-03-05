package main.java.kamino.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {

        KaminoServer kaminoServer = new KaminoServer();
        ServerSocket serverSocket = new ServerSocket(kaminoServer.serverPort);
        Socket socket = null;

        kaminoServer.loadConfig();

        while (true) {
            socket = serverSocket.accept();
            kaminoServer.newClientConnection(socket);
        }

    }
}
