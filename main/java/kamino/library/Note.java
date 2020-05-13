package main.java.kamino.library;

import java.io.Serializable;
import java.util.Date;

/* Note object */
/* ----------------------------------------------------- */
public class Note implements Serializable {

    public String noteId;            /* Unique note ID */
    public String userId;            /* Corresponding user ID */
    public String noteTitle;         /* Title of the note */
    public String noteDesc;          /* Description of the note */

    public Date noteCreation;        /* Date the note was created */
    public Date noteAccessed;        /* Date the note was last accessed */

    public String noteUrl;           /* Local server URL to the note */

    public String noteContents;

    public Note(String userId, String noteTitle, String noteContents){
        this.userId = userId;
        this.noteTitle = noteTitle;
        this.noteContents = noteContents;
    }

    public Note(String userId, String noteId, String noteTitle, String noteUrl, String noteContents){
        this.userId = userId;
        this.noteId = noteId;
        this.noteUrl = noteUrl;
        this.noteTitle = noteTitle;
        this.noteContents = noteContents;
    }

}
