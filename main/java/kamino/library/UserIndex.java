package main.java.kamino.library;

import java.io.Serializable;
import java.util.ArrayList;

/* User index packet (notes) */
/* ----------------------------------------------------- */
public class UserIndex implements Serializable{

    public ArrayList<Note> notes;

    public UserIndex(ArrayList<Note> notes){
        this.notes = notes;
    }

}
