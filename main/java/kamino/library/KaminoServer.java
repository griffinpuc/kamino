package main.java.kamino.library;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/* Kamino Server */
/* Contains all code for the kamino server to run */
public class KaminoServer {

    public int serverPort;
    private MysqlDataSource dataSource;

    public KaminoServer(){
        this.dataSource = new MysqlDataSource();
    }

    /* Load configuration parameters from json configuration file */
    /* ---------------------------------------------------------- */
    public void loadConfig() throws IOException {
        JSONObject obj = new JSONObject(Files.readString(Paths.get("C:\\Users\\griff\\Workspace\\Kamino\\ServerConfig.json"), StandardCharsets.US_ASCII));

        /* Set database connection parameters */
        this.dataSource.setUser(obj.getJSONObject("databaseConnection").getString("dbUser"));
        this.dataSource.setPassword(obj.getJSONObject("databaseConnection").getString("dbPassword"));
        this.dataSource.setServerName(obj.getJSONObject("databaseConnection").getString("serverName"));

        /* Set server connection parameters */
        this.serverPort = Integer.parseInt(obj.getJSONObject("serverConnection").getString("serverPort"));
    }

    /* Create a new client connection thread evey time someone logs in */
    /* --------------------------------------------------------------- */
    public void newClientConnection(Socket socket){
        pushNotification("JOIN", "New client joined the server");
        new ClientConnection(socket, this.dataSource).start();
    }

    /* Push formatted notification to server console */
    /* --------------------------------------------- */
    public void pushNotification(String type, String message){
        String dateTime = new SimpleDateFormat("dd-MMM-yyyy:hh:mm:ss").format(new Date());
        System.out.println("["+dateTime+"] "+type+": "+message);
    }

}
