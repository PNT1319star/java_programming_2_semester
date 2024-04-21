package interaction;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return username + ": " + password;
    }

    @Override
    public int hashCode() {
        return username.hashCode() + password.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object instanceof User) {
            User userObject = (User) object;
            return username.equals(userObject.getUsername()) && password.equals(userObject.getPassword());
        }
        return false;
    }
}
