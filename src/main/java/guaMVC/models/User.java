package guaMVC.models;

public class User {
    public Integer id;
    public String username;
    public String password;
    public UserRole role;

    public User() {

    }

    @Override
    public String toString() {
        String s = String.format(
                "(id: %s, username: %s, password: %s, role: %s)",
                this.id,
                this.username,
                this.password,
                this.role
        );

        return s;
    }
}
