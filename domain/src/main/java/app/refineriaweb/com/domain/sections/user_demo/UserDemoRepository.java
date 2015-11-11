package app.refineriaweb.com.domain.sections.user_demo;

import java.util.List;

import app.refineriaweb.com.domain.foundation.Repository;
import rx.Observable;

public interface UserDemoRepository extends Repository {
    Observable<UserDemoEntity> searchByUserName(String nameUser);
    Observable<List<UserDemoEntity>> askForUsers();
    Observable<UserDemoEntity> getSelectedUserDemoList();
    Observable saveSelectedUserDemoList(UserDemoEntity user);
}
