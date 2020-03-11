package main.java.kamino.library;

import java.sql.*;

public class DatabaseConnection {

    public Connection dbConnection;

    public DatabaseConnection(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
    }

    /* Add a new user account to the database */
    /* -------------------------------------- */
    public void addUser(String userName, String userEmail, String userPass){

        String sql =
                "INSERT INTO kamino_schema.users " +
                "(username, email, password)" +
                "VALUES (?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement stmt = dbConnection.prepareStatement(sql);

            stmt.setString(1, userName);
            stmt.setString(2, userEmail);
            stmt.setString(3, userPass);

            stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    /* Authenticate a set of credentials against the database */
    /* ------------------------------------------------------ */
    public int authUser(String userName, String userPass){

        String sql =
                "SELECT userID, username, password " +
                "FROM kamino_schema.users " +
                "WHERE username = ? AND password = ?; ";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement stmt = dbConnection.prepareStatement(sql);

            stmt.setString(1, userName);
            stmt.setString(2, userPass);

            System.out.println(stmt);

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                return rs.getInt("userID");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return 1;

    }

}
