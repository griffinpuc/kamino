package main.java.kamino.library;

import java.util.Date;

public class Note {

    private String noteId;            /* Unique note ID */
    private String userId;            /* Corresponding user ID */
    private String noteTitle;         /* Title of the note */
    private String noteDesc;          /* Description of the note */

    private Date noteCreation;        /* Date the note was created */
    private Date noteAccessed;        /* Date the note was last accessed */

    private String noteUrl;           /* Local server URL to the note */

}
