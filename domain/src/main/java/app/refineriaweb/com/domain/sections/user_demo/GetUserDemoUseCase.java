package app.refineriaweb.com.domain.sections.user_demo;

import javax.inject.Inject;

import app.refineriaweb.com.domain.foundation.UseCase;
import rx.Subscriber;

public class GetUserDemoUseCase extends UseCase<UserDemoAgent> {

    @Inject public GetUserDemoUseCase(UserDemoAgent agent) {
        super(agent);
    }

    public void getUser(String name, Subscriber<UserDemoEntity> subscriber) {
        agent.getUser(name, subscriber);
    }

}
