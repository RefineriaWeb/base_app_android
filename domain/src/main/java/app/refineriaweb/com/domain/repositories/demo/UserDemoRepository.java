package app.refineriaweb.com.domain.repositories.demo;

import app.refineriaweb.com.domain.entities.UserDemo;
import app.refineriaweb.com.domain.repositories.Repository;
import rx.Observable;

public interface UserDemoRepository extends Repository {
    Observable<UserDemo> askForUser(String nameUser);
    Observable<UserDemo> askForCachedUser();
}
