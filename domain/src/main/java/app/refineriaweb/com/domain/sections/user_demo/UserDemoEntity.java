package app.refineriaweb.com.domain.sections.user_demo;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class UserDemoEntity {
    private final int id;
    private final String login = "", bio = "";
}
