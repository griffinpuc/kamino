package main.java.kamino.server;

import main.java.kamino.library.Hash;
import main.java.kamino.library.RequestAuth;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class ClientConnection extends Thread {

    protected Socket socket;
    private int userId;
    private DatabaseConnection connectionContext;
    private Hash hash;

    public ClientConnection(Socket socket, Connection dbConnection) {
        try{
            this.socket = socket;
            this.connectionContext = new DatabaseConnection(dbConnection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectionContext.addUser(hash.hashString("griffin"), hash.hashString("griffin"));

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
        Integer returned = connectionContext.authUser(authRequest.usernameHash, authRequest.passwordHash);
        if(returned != null){ authRequest.authenticate(); }

        try {
            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
            ObjectOutputStream oStream = new ObjectOutputStream(dOut);
            oStream.writeObject(authRequest);
            oStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
