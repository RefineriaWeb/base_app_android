package app.refineriaweb.com.domain.sections.user_demo;


public class UserDemoEntity {
    private final int id;
    private final String login;
    private String avatar_url = "";

    protected UserDemoEntity(int id, String login) {
        this.id = id;
        this.login = login;
    }

    public String getAvatarUrl() {
        if (avatar_url.isEmpty()) return avatar_url;
        return avatar_url.split("\\?")[0];
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
}
