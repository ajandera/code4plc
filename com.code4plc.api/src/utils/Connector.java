package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface Connector {
    static Connection connect() throws SQLException {
        // Setup the connection with the DB
        return DriverManager.getConnection("jdbc:mysql://db:3306/code4plc?user=root&password=root");
    }
}
