package app.refineriaweb.com.domain.sections.user_demo;

import javax.inject.Inject;

import app.refineriaweb.com.domain.foundation.UseCase;
import rx.Subscriber;

public class GetCachedUserDemoUseCase extends UseCase<UserDemoAgent> {
    @Inject public GetCachedUserDemoUseCase(UserDemoAgent agent) {
        super(agent);
    }

    public void getCachedUser(Subscriber<UserDemoEntity> subscriber) {
        agent.getCachedUser(subscriber);
    }
}
