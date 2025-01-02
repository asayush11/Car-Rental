package src;

public class User {
    private final String name;
    private final String license;
    private final String email;

    public User(String name, String license, String email) {
        this.name = name;
        this.license = license;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getLicense() {
        return license;
    }

    public String getEmail() {
        return email;
    }
}
