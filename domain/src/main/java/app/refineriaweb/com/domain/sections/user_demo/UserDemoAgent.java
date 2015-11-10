package app.refineriaweb.com.domain.sections.user_demo;

import javax.inject.Inject;

import app.refineriaweb.com.domain.foundation.Agent;
import app.refineriaweb.com.domain.foundation.schedulers.ObserveOn;
import app.refineriaweb.com.domain.foundation.schedulers.SubscribeOn;
import rx.Subscriber;

public class UserDemoAgent extends Agent<UserDemoRepository> {

    @Inject public UserDemoAgent(UserDemoRepository repository, SubscribeOn subscribeOn, ObserveOn observeOn) {
        super(repository, subscribeOn, observeOn);
    }

    public void getUser(String name, Subscriber<UserDemoEntity> subscriber) {
        execute(repository.askForUser(name), subscriber);
    }

    public void getCachedUser(Subscriber<UserDemoEntity> subscriber) {
        execute(repository.askForCachedUser(), subscriber);
    }

}
