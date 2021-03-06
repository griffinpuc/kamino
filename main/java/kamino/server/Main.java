package main.java.kamino.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {

        /* Start the server */
        KaminoServer kaminoServer = new KaminoServer();
        /* Load config */
        kaminoServer.loadConfig();
        ServerSocket serverSocket = new ServerSocket(kaminoServer.serverPort);
        Socket socket = null;

        System.out.println("Kamino server started");
        System.out.println("On port: "+ kaminoServer.serverPort);

        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            /* New client thread for every new connection */
            kaminoServer.newClientConnection(socket);
        }

    }
}
