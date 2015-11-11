package app.refineriaweb.com.domain.sections.user_demo.detail;

import javax.inject.Inject;

import app.refineriaweb.com.domain.foundation.UseCase;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoAgent;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;
import rx.Subscriber;

public class GetSelectedDemoUserListUseCase extends UseCase<UserDemoAgent> {
    @Inject public GetSelectedDemoUserListUseCase(UserDemoAgent agent) {
        super(agent);
    }

    public void getCachedUser(Subscriber<UserDemoEntity> subscriber) {
        agent.getSelectedDemoUserList(subscriber);
    }
}
