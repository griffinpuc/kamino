package main.java.kamino.server;

import java.sql.*;

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

    /* Authenticate a set of credentials against the database */
    /* ------------------------------------------------------ */
    public Integer authUser(String userName, String userPass){

        String sql =
                "SELECT userID, username, password " +
                "FROM kamino_schema.users " +
                "WHERE username = ? AND password = ?";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement stmt = dbConnection.prepareStatement(sql);

            stmt.setString(1, userName);
            stmt.setString(2, userPass);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                return rs.getInt("userID");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

}
