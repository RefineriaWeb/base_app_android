package app.refineriaweb.com.domain.interactors.demo;

import javax.inject.Inject;

import app.refineriaweb.com.domain.entities.UserDemo;
import app.refineriaweb.com.domain.interactors.UseCase;
import app.refineriaweb.com.domain.repositories.demo.UserDemoRepository;
import app.refineriaweb.com.domain.schedulers.ObserveOn;
import app.refineriaweb.com.domain.schedulers.SubscribeOn;
import rx.Observable;

public class GetUserDemoUseCase extends UseCase<UserDemo, UserDemoRepository> {
    private String userName;

    @Inject public GetUserDemoUseCase(UserDemoRepository userDemoRepository, SubscribeOn subscribeOn, ObserveOn observeOn) {
        super(userDemoRepository, subscribeOn, observeOn);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override protected Observable<UserDemo> buildUseCaseObservable() {
        return repository.askForUser(userName);
    }
}
