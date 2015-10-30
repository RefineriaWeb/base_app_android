package app.refineriaweb.com.domain.interactors.demo;

import javax.inject.Inject;

import app.refineriaweb.com.domain.entities.UserDemo;
import app.refineriaweb.com.domain.interactors.UseCase;
import app.refineriaweb.com.domain.repositories.demo.UserDemoRepository;
import app.refineriaweb.com.domain.schedulers.ObserveOn;
import app.refineriaweb.com.domain.schedulers.SubscribeOn;
import rx.Observable;

public class GetCachedUserDemoUseCase extends UseCase<UserDemo, UserDemoRepository> {
    @Inject public GetCachedUserDemoUseCase(UserDemoRepository userDemoRepository, SubscribeOn subscribeOn, ObserveOn observeOn) {
        super(userDemoRepository, subscribeOn, observeOn);
    }

    @Override protected Observable<UserDemo> buildUseCaseObservable() {
        return repository.askForCachedUser();
    }
}
