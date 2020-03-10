package main.java.kamino.library;

import javax.sql.DataSource;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ClientConnection extends Thread {

    protected Socket socket;
    private DatabaseConnection connectionContext;

    public ClientConnection(Socket socket, DataSource dataSource) {
        try{
            this.socket = socket;
            this.connectionContext = new DatabaseConnection(dataSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        run();
    }

    public void run() {
        try {
            DataInputStream dIn = new DataInputStream(socket.getInputStream());
            ObjectInputStream iStream = new ObjectInputStream(dIn);

            System.out.println(iStream.readObject());
            System.out.println("here");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
