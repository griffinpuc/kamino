package main.java.kamino.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(8888);
        Socket clientConnection = server.accept();

    }
}
