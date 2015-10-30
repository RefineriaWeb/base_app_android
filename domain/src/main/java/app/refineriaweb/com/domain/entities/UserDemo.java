package app.refineriaweb.com.domain.entities;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class UserDemo {
    private final int id;
    private final String login = "", bio = "";
}
