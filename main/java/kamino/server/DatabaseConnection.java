package main.java.kamino.server;

import main.java.kamino.library.Note;
import main.java.kamino.library.UserIndex;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {

    public Connection dbConnection;

    public DatabaseConnection(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
    }

    /* Add a new user account to the database */
    /* -------------------------------------- */
    public void addUser(String userName, String userPass){

        String sql =
                "INSERT INTO kamino_schema.users " +
                "(username, password)" +
                "VALUES (?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement stmt = dbConnection.prepareStatement(sql);

            stmt.setString(1, userName);
            stmt.setString(2, userPass);

            stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    /* Add note to table */
    /* ----------------------------------------------------- */
    public void addNote(String userId, String noteTitle, String noteUrl){

        String sql =
                "INSERT INTO kamino_schema.notes " +
                "(userId, noteTitle, noteUrl)" +
                "VALUES (?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement stmt = dbConnection.prepareStatement(sql);

            stmt.setString(1, userId);
            stmt.setString(2, noteTitle);
            stmt.setString(3, noteUrl);

            stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    /* Authenticate a set of credentials against the database */
    /* ------------------------------------------------------ */
    public Integer authUser(String userName, String userPass){

        String sql =
                "SELECT userId, username, password " +
                "FROM kamino_schema.users " +
                "WHERE username = ? AND password = ?";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement stmt = dbConnection.prepareStatement(sql);

            stmt.setString(1, userName);
            stmt.setString(2, userPass);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                return rs.getInt("userId");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;

    }


    /* Get user index (notes) */
    /* ----------------------------------------------------- */
    public UserIndex getIndex(int userId){

        ArrayList<Note> notes = new ArrayList<>();

        String sql =
                "SELECT noteId, userId, noteTitle, noteUrl " +
                        "FROM kamino_schema.notes " +
                        "WHERE userId = ?";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement stmt = dbConnection.prepareStatement(sql);

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Note foundNote = new Note(rs.getString("userId"), rs.getString("noteId"), rs.getString("noteTitle"), rs.getString("noteUrl"), null);
                notes.add(foundNote);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return new UserIndex(notes);

    }

}
