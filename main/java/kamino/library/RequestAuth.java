package main.java.kamino.library;

import java.io.Serializable;

/* Request authentication packet */
/* ----------------------------------------------------- */
public class RequestAuth implements Serializable {

    public String usernameHash;
    public String passwordHash;

    private boolean authenticated;
    private int userId;

    public RequestAuth(String username, String password){
        this.authenticated = false;
        this.usernameHash = username;
        this.passwordHash = password;
    }

    public void authenticate(){
        this.authenticated = true;
    }

    public void setUserId(int userId){ this.userId = userId; }

    public boolean isAuth(){
        return this.authenticated;
    }
    public int getUserId(){ return this.userId; }

}