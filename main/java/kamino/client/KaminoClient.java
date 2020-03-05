package main.java.kamino.client;

import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KaminoClient {

    private Socket serverConnection;
    private String serverAddress;
    private String serverPort;

    //Load configuration settings from the config json file
    public void loadConfig() throws IOException {
        JSONObject obj = new JSONObject(Files.readString(Paths.get("Config.json"), StandardCharsets.US_ASCII));
        this.serverAddress = obj.getJSONObject("connection").getString("serverAddress");
        this.serverPort = obj.getJSONObject("connection").getString("serverPort");
    }

    public void connectClient() throws IOException {
        this.serverConnection = new Socket(this.serverAddress, Integer.parseInt(this.serverPort));

    }

    //Authenticate and login in a user
    public boolean loginUsrer(String username, String password) throws IOException {

        Socket connectionToTheServer = new Socket("localhost", 8888); // First param: server-address, Second: the port

        return true;
    }

}
