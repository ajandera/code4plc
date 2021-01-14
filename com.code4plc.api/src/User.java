import java.sql.Timestamp;

/**
 * User object
 */
public class User {
    String id;
    String username;
    String password;
    String salt;
    Timestamp last_login;
}
