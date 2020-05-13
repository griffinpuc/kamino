package main.java.kamino.client;

import main.java.kamino.library.*;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class KaminoClient {

    private Socket serverConnection;
    private String serverAddress;
    private int serverPort;

    private String usernameHash;
    private String passwordHash;
    private int userId;

    public KaminoClient(){
        loadConfig();
        authClient();
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
        try {
            this.serverConnection = new Socket(this.serverAddress, this.serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Send a packet to the server, serializable objects */
    /* ----------------------------------------------------- */
    public Object sendPacket(Object obj){
        try {
            DataOutputStream dOut = new DataOutputStream(serverConnection.getOutputStream());
            ObjectOutputStream oStream = new ObjectOutputStream(dOut);

            if(obj instanceof RequestAuth){
                oStream.writeObject(obj);
            }

            if(obj instanceof Note){
                oStream.writeObject(obj);
            }

            if(obj instanceof RequestIndex){
                oStream.writeObject(obj);
            }

            oStream.flush();
            DataInputStream dIn = new DataInputStream(serverConnection.getInputStream());
            ObjectInputStream iStream = new ObjectInputStream(dIn);
            Object nextObj = iStream.readObject();

            if(nextObj instanceof RequestAuth){
                RequestAuth authRequest = (RequestAuth) nextObj;
                if((authRequest).isAuth()){
                    this.userId = authRequest.getUserId();
                }
                return authRequest;
            }

            if(nextObj instanceof UserIndex){
                UserIndex userIndex = (UserIndex) nextObj;
                return userIndex;
            }

            if(nextObj instanceof Success){
                return null;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    /* Authenticate login credentials against server */
    /* ----------------------------------------------------- */
    public void authClient(){

        Scanner in = new Scanner(System.in);
        Hash hash = new Hash();
        System.out.println("Kamino Notes");

        while(true){
            System.out.print("Username: ");
            usernameHash = hash.hashString(in.nextLine());
            System.out.print("Password: ");
            passwordHash = hash.hashString(in.nextLine());

            RequestAuth reqAuth = new RequestAuth(usernameHash, passwordHash);
            reqAuth = (RequestAuth) sendPacket(reqAuth);

            if(reqAuth.isAuth()){
                System.out.println("Connected!");
                break;
            }
            else{
                System.out.println("Incorrect credentials, try again.");
            }
        }
    }

    /* Get the index of users notes from server */
    /* ----------------------------------------------------- */
    public UserIndex getIndex(){
        return (UserIndex) sendPacket(new RequestIndex((this.userId)));
    }

    /* Create a new note and send it to the server */
    /* ----------------------------------------------------- */
    public void createNote(String noteTitle, String noteContents){
        Note newNote = new Note(Integer.toString(this.userId), noteTitle, noteContents);
        sendPacket(newNote);
    }

    /* Start the login gui and begin authentication process */
    /* ---------------------------------------------------- */
    /*
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
     */

    public void startMainGUI(){
        MainGui mainGui = new MainGui();
    }

}
