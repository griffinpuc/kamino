package main.java.kamino.server;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    public Connection databaseContext;

    public DatabaseConnection(DataSource dataSource) throws SQLException {
        this.databaseContext = dataSource.getConnection();
    }

    //public boolean validateUser(String username, String password){

    //}

}
