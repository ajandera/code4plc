package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface Connector {
    public static Connection connect() throws SQLException {
        // Setup the connection with the DB
        return DriverManager.getConnection("jdbc:mysql://db/code4plc?user=root&password=root");
    }
}
