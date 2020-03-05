package main.java.kamino.server;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.json.JSONObject;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        JSONObject obj = new JSONObject(Files.readString(Paths.get("Config.json"), StandardCharsets.US_ASCII));

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
        new ClientConnection(socket).start();
    }

}
