package main.java.kamino.library;

import com.mysql.cj.log.Log;
import main.java.kamino.client.Login;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
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

    public void connectClient() {
        try {
            serverConnection = new Socket(this.serverAddress, this.serverPort);
            DataOutputStream out = new DataOutputStream(serverConnection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGUI(){
        Login log = new Login();
        while(true){
            if(log.username != null){
                this.usernameHash = log.username;
                this.passwordHash = log.password;
                log.loginFrame.setVisible(false);
            }
        }
    }

}
