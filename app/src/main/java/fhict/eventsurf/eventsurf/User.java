package fhict.eventsurf.eventsurf;

/**
 * Created by denis on 4/5/2018.
 */

public class User {
    public String email;
    public String password;
    public String name;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
