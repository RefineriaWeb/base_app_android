package app.refineriaweb.com.domain.sections.user_demo;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class UserDemoEntity {
    private final int id;
    private final String login = "", bio = "";
    private String avatar_url = "";

    public String getAvatar_url() {
        if (avatar_url == null || avatar_url.isEmpty()) return "";
        return avatar_url.split("\\?")[0];
    }
}
