package main.java.kamino.library;


import main.java.kamino.client.LoginGui;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KaminoClient {

    private Socket serverConnection;
    private String serverAddress;
    private int serverPort;

    private String usernameHash;
    private String passwordHash;

    public KaminoClient(){
        loadConfig();
    }

    //Load configuration settings from the config json file
    private void loadConfig() {
        JSONObject obj = null;
        try {
            obj = new JSONObject(Files.readString(Paths.get("C:\\Users\\griff\\Workspace\\Kamino\\ClientConfig.json"), StandardCharsets.US_ASCII));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.serverAddress = obj.getJSONObject("connection").getString("serverAddress");
        this.serverPort = Integer.parseInt(obj.getJSONObject("connection").getString("serverPort"));
    }

    public void connectClient(String username, String password) {
        try {
            serverConnection = new Socket(this.serverAddress, this.serverPort);
            DataOutputStream dOut = new DataOutputStream(serverConnection.getOutputStream());
            ObjectOutputStream oStream = new ObjectOutputStream(dOut);

            oStream.writeObject(new RequestAuth(username, password));
            oStream.flush();

            DataInputStream dIn = new DataInputStream(serverConnection.getInputStream());
            ObjectInputStream iStream = new ObjectInputStream(dIn);

            System.out.println(iStream.readObject());
            System.out.println("got here");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void startGUI(){
        LoginGui loginGui = new LoginGui();
        Hash hash = new Hash();

        loginGui.SubmitLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("".equals(loginGui.UnameField.getText()) || "".equals(loginGui.PassField.getPassword()) ){
                    JOptionPane.showMessageDialog(null, "Username or password field is incorrect.");
                }
                else{
                    usernameHash = hash.hashString(loginGui.UnameField.getText());
                    passwordHash = hash.hashString(loginGui.PassField.getPassword().toString());
                    connectClient(usernameHash, passwordHash);
                }
            }
        });
    }

}
