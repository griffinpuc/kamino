package main.java.kamino.client;

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

    //Load configuration settings from the config json file
    public void loadConfig() throws IOException {
        JSONObject obj = new JSONObject(Files.readString(Paths.get("C:\\Users\\griff\\Workspace\\Kamino\\ClientConfig.json"), StandardCharsets.US_ASCII));
        this.serverAddress = obj.getJSONObject("connection").getString("serverAddress");
        this.serverPort = Integer.parseInt(obj.getJSONObject("connection").getString("serverPort"));
    }

    public void connectClient() throws IOException {
        serverConnection = new Socket(this.serverAddress, this.serverPort);
        DataOutputStream out = new DataOutputStream(serverConnection.getOutputStream());
        out.writeUTF("yo");
    }

    //Authenticate and login in a user
    public boolean loginUser(String username, String password) throws IOException {

        Socket connectionToTheServer = new Socket("localhost", 8888); // First param: server-address, Second: the port

        return true;
    }

}
