package app.refineriaweb.com.domain.sections.user_demo.list;

import javax.inject.Inject;

import app.refineriaweb.com.domain.foundation.UseCase;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoAgent;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;
import rx.Subscriber;

public class SaveUserDemoSelectedListUseCase extends UseCase<UserDemoAgent> {

    @Inject public SaveUserDemoSelectedListUseCase(UserDemoAgent agent) {
        super(agent);
    }

    public void saveUser(UserDemoEntity user, Subscriber subscriber) {
        agent.saveSelectedUserDemoList(user, subscriber);
    }
}
