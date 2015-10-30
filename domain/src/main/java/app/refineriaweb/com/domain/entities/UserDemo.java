package app.refineriaweb.com.domain.entities;

public class UserDemo {
    private final int id;
    private final String login, bio;

    public UserDemo(int id, String login, String bio) {
        this.id = id;
        this.login = login;
        this.bio = bio;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return login;
    }

    public String getBio() {
        return bio != null ? bio : "";
    }


}
