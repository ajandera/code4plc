package model;

import java.sql.Timestamp;

/**
 * Setting object
 */
public class User {
    String id;
    String username;
    String password;
    String salt;
    Timestamp last_login;

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLast_login(Timestamp last_login) {
        this.last_login = last_login;
    }
}
