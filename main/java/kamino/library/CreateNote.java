package main.java.kamino.library;

import java.io.Serializable;

/* Create note packet */
/* ----------------------------------------------------- */
public class CreateNote implements Serializable {

    public String userId;
    public String noteId;
    public String noteTitle;
    public String noteContents;

}
