package main.java.kamino.library;

import java.io.Serializable;

public class RequestAuth implements Serializable {

    public String usernameHash;
    public String passwordHash;

    private boolean authenticated;

    public RequestAuth(String username, String password){
        this.authenticated = false;
        this.usernameHash = username;
        this.passwordHash = password;
    }

    public void authenticate(){
        this.authenticated = true;
    }

}