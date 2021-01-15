import utils.Connector;

import java.sql.*;
import java.util.*;

/**
 * Database wrapper class
 */
public class Database implements Connector {

    /**
     * @param sql
     * @param params
     */
    public static ResultSet query(String sql, List<String> params) throws SQLException {
        try {
            Connection connect = Connector.connect();
            // PreparedStatements can use variables and are more efficient
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            System.out.println(sql);

            // Set parameters if needed
            int i = 1;
            for (String param : params) {
                preparedStatement.setString(i, param);
                i++;
            }

            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Insert data to database.
     * @param sql
     * @param params
     * @return
     */
    public static boolean insert(String sql, List<String> params) throws SQLException {
        try {
            Connection connect = Connector.connect();

            // PreparedStatements can use variables and are more efficient
            PreparedStatement preparedStatement = connect.prepareStatement(sql);

            // Set parameters if needed
            int i = 1;
            for (String param : params) {
                preparedStatement.setString(i, param);
                i++;
            }

            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
