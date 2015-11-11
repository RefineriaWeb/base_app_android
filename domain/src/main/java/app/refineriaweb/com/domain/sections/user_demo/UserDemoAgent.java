package app.refineriaweb.com.domain.sections.user_demo;

import java.util.List;

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
        execute(repository.searchByUserName(name), subscriber);
    }

    public void getSelectedDemoUserList(Subscriber<UserDemoEntity> subscriber) {
        execute(repository.getSelectedUserDemoList(), subscriber);
    }

    public void getUsers(Subscriber<List<UserDemoEntity>> subscriber) {
        execute(repository.askForUsers(), subscriber);
    }

    public void saveSelectedUserDemoList(UserDemoEntity user, Subscriber subscriber) {
        execute(repository.saveSelectedUserDemoList(user), subscriber);
    }
}
