package main.java.kamino.server;

import main.java.kamino.library.*;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private String storageUrl;

    /* Load configuration parameters from json configuration file */
    /* ---------------------------------------------------------- */
    public void loadConfig() throws IOException {
        JSONObject obj = new JSONObject(Files.readString(Paths.get("C:\\Users\\griff\\Workspace\\kamino-project\\src\\main\\java\\kamino\\server\\Config.json"), StandardCharsets.US_ASCII));

        /* Set database connection parameters */
        this.dbUser = obj.getJSONObject("databaseConnection").getString("dbUser");
        this.dbPass = obj.getJSONObject("databaseConnection").getString("dbPassword");
        this.dbUrl =  obj.getJSONObject("databaseConnection").getString("dbUrl");

        /* Note storage directory url */
        this.storageUrl = obj.getJSONObject("storage").getString("storageUrl");

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
        new ClientConnection(socket, dbConnection, storageUrl).start();
    }

    /* Push formatted notification to server console */
    /* --------------------------------------------- */
    public void pushNotification(String type, String message){
        String dateTime = new SimpleDateFormat("dd-MMM-yyyy:hh:mm:ss").format(new Date());
        System.out.println("["+dateTime+"] "+type+": "+message);
    }

}

/* Contains all data for a client connection */
/* ----------------------------------------------------- */
class ClientConnection extends Thread {

    protected Socket socket;
    private int userId;
    private String storageurl;
    private DatabaseConnection connectionContext;
    private Hash hash = new Hash();

    public ClientConnection(Socket socket, Connection dbConnection, String storageurl) {
        try{
            this.socket = socket;
            this.connectionContext = new DatabaseConnection(dbConnection);
            this.storageurl = storageurl;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //connectionContext.addUser(hash.hashString("griffin"), hash.hashString("password"));


        /* Check for incoming packets */
        /* ----------------------------------------------------- */
        while(true){
            DataInputStream dIn = null;
            try {
                dIn = new DataInputStream(socket.getInputStream());
                ObjectInputStream iStream = new ObjectInputStream(dIn);

                Object nextObj = iStream.readObject();

                /* Authentication packet */
                /* ----------------------------------------------------- */
                if(nextObj instanceof RequestAuth){
                    System.out.println("AUTH REQUEST");
                    RequestAuth authRequest = authenticateClient((RequestAuth) nextObj);
                    sendAuthGrant(authRequest);

                }

                /* Request notes packet */
                /* ----------------------------------------------------- */
                if(nextObj instanceof RequestIndex){
                    System.out.println("INDEX REQUEST");
                    sendUserIndex(connectionContext.getIndex(((RequestIndex) nextObj).userId));
                }

                /* Add note packet */
                /* ----------------------------------------------------- */
                if(nextObj instanceof Note){
                    System.out.println("ADD NOTE");
                    Note newNote = (Note) nextObj;
                    createNewNote(newNote);
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Client connection lost");
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    /* Create a new note */
    /* ----------------------------------------------------- */
    public void createNewNote(Note newnote){

        Path path = Paths.get(storageurl + "\\" + userId);

        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File file = new File(path.toString() + "\\" + newnote.noteTitle);

        try {
            if (file.createNewFile())
            {
            } else {

            }

            FileWriter writer = new FileWriter(file);
            writer.write(newnote.noteContents);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        connectionContext.addNote(newnote.userId, newnote.noteTitle, file.getPath());

        try {
            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
            ObjectOutputStream oStream = new ObjectOutputStream(dOut);
            oStream.writeObject(new Success());
            oStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Authenticate the client connection */
    /* ----------------------------------------------------- */
    public RequestAuth authenticateClient(RequestAuth authRequest) {
        Integer returned = connectionContext.authUser(authRequest.usernameHash, authRequest.passwordHash);
        if (returned != null) {
            authRequest.authenticate();
            authRequest.setUserId(returned);
        }

        return authRequest;
    }

    /* Send authorization grant packet */
    /* ----------------------------------------------------- */
    public void sendAuthGrant(RequestAuth authRequest){
        try {
            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
            ObjectOutputStream oStream = new ObjectOutputStream(dOut);
            oStream.writeObject(authRequest);
            oStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Send user index packet */
    /* ----------------------------------------------------- */
    public void sendUserIndex(UserIndex index){

        for(Note note : index.notes){
            try {
                note.noteContents = Files.readString(Paths.get(note.noteUrl), StandardCharsets.US_ASCII);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
            ObjectOutputStream oStream = new ObjectOutputStream(dOut);
            oStream.writeObject(index);
            oStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
