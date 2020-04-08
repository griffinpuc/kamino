package main.java.kamino.client;


import main.java.kamino.client.LoginGui;
import main.java.kamino.client.MainGui;
import main.java.kamino.library.Hash;
import main.java.kamino.library.RequestAuth;
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

    /* Load configuration settings from the config json file */
    /* ----------------------------------------------------- */
    private void loadConfig() {
        JSONObject obj = null;
        try {
            obj = new JSONObject(Files.readString(Paths.get("C:\\Users\\griff\\Workspace\\kamino-project\\src\\main\\java\\kamino\\client\\Config.json"), StandardCharsets.US_ASCII));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.serverAddress = obj.getJSONObject("connection").getString("serverAddress");
        this.serverPort = Integer.parseInt(obj.getJSONObject("connection").getString("serverPort"));
    }

    /* Authenticate the client with the server */
    /* --------------------------------------- */
    public boolean connectClient(String username, String password) {
        boolean retval = false;
        try {
            serverConnection = new Socket(this.serverAddress, this.serverPort);
            DataOutputStream dOut = new DataOutputStream(serverConnection.getOutputStream());
            ObjectOutputStream oStream = new ObjectOutputStream(dOut);

            oStream.writeObject(new RequestAuth(username, password));
            oStream.flush();

            DataInputStream dIn = new DataInputStream(serverConnection.getInputStream());
            ObjectInputStream iStream = new ObjectInputStream(dIn);
            Object nextObj = iStream.readObject();

            if(nextObj instanceof RequestAuth){
                if(((RequestAuth) nextObj).isAuth()){
                    retval = true;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return retval;
    }

    /* Start the login gui and begin authentication process */
    /* ---------------------------------------------------- */
    public void startGUI(){
            LoginGui loginGui = new LoginGui();
            Hash hash = new Hash();

            loginGui.SubmitLogin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    usernameHash = hash.hashString(loginGui.UnameField.getText());
                    passwordHash = hash.hashString(String.valueOf(loginGui.PassField.getPassword()));
                    if(!connectClient(usernameHash, passwordHash)){
                        JOptionPane.showMessageDialog(null, "Incorrect credentials");
                        loginGui.loginFrame.setVisible(false);
                    }
                }
            });

    }

    public void startMainGUI(){
        MainGui mainGui = new MainGui();
    }

}
