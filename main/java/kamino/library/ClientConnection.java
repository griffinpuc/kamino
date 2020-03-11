package main.java.kamino.library;

import javax.sql.*;
import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class ClientConnection extends Thread {

    protected Socket socket;
    private int userId;
    private DatabaseConnection connectionContext;

    public ClientConnection(Socket socket, Connection dbConnection) {
        try{
            this.socket = socket;
            this.connectionContext = new DatabaseConnection(dbConnection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Hash h = new Hash();
        String x = h.hashString("admin");
        String g = h.hashString("email@mail.com");
        String y = h.hashString("password");
        //connectionContext.addUser(x, g, y);

        while(true){
            DataInputStream dIn = null;
            try {
                dIn = new DataInputStream(socket.getInputStream());
                ObjectInputStream iStream = new ObjectInputStream(dIn);

                Object nextObj = iStream.readObject();

                if(nextObj instanceof RequestAuth){
                    System.out.println("AUTH REQUEST");
                    authenticateClient((RequestAuth) nextObj);
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void authenticateClient(RequestAuth authRequest) {
        System.out.println(connectionContext.authUser(authRequest.usernameHash, authRequest.usernameHash));
    }

}
