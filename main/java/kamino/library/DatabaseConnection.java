package main.java.kamino.library;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    public Connection databaseContext;

    public DatabaseConnection(DataSource dataSource) throws SQLException {
        this.databaseContext = dataSource.getConnection();
    }

    public void validateUser(String username, String password){
        try{
            Statement stmt = this.databaseContext.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID FROM USERS");

            rs.close();
            stmt.close();
        }catch (SQLException e) {
            e.printStackTrace();

        }
    }

}
