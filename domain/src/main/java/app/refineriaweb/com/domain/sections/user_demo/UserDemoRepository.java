package app.refineriaweb.com.domain.sections.user_demo;

import app.refineriaweb.com.domain.foundation.Repository;
import rx.Observable;

public interface UserDemoRepository extends Repository {
    Observable<UserDemoEntity> askForUser(String nameUser);
    Observable<UserDemoEntity> askForCachedUser();
}
