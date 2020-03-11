package main.java.kamino.library;

import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;

/* Kamino Server */
/* Contains all code for the kamino server to run */
public class KaminoServer {

    public int serverPort;
    private String dbUser;
    private String dbPass;
    private String dbUrl;

    /* Load configuration parameters from json configuration file */
    /* ---------------------------------------------------------- */
    public void loadConfig() throws IOException {
        JSONObject obj = new JSONObject(Files.readString(Paths.get("C:\\Users\\griff\\Workspace\\kamino-project\\src\\main\\java\\kamino\\server\\Config.json"), StandardCharsets.US_ASCII));

        /* Set database connection parameters */
        this.dbUser = obj.getJSONObject("databaseConnection").getString("dbUser");
        this.dbPass = obj.getJSONObject("databaseConnection").getString("dbPassword");
        this.dbUrl =  obj.getJSONObject("databaseConnection").getString("dbUrl");

        /* Set server connection parameters */
        this.serverPort = Integer.parseInt(obj.getJSONObject("serverConnection").getString("serverPort"));
    }

    /* Create a new client connection thread evey time someone logs in */
    /* --------------------------------------------------------------- */
    public void newClientConnection(Socket socket){
        pushNotification("JOIN", "New client joined the server");
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        new ClientConnection(socket, dbConnection).start();
    }

    /* Push formatted notification to server console */
    /* --------------------------------------------- */
    public void pushNotification(String type, String message){
        String dateTime = new SimpleDateFormat("dd-MMM-yyyy:hh:mm:ss").format(new Date());
        System.out.println("["+dateTime+"] "+type+": "+message);
    }

}
