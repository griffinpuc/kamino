package main.java.kamino.server;

import java.net.Socket;

public class ClientConnection extends Thread {

    protected Socket socket;

    public ClientConnection(Socket socket){
        this.socket = socket;
    }

}
