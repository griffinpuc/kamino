package main.java.kamino.library;

import java.io.Serializable;

/* Request users index packet */
/* ----------------------------------------------------- */
public class RequestIndex implements Serializable {

    public int userId;

    public RequestIndex(int userId){
        this.userId = userId;
    }

}
