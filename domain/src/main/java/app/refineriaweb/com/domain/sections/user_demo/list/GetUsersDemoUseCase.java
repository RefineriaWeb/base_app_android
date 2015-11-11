package app.refineriaweb.com.domain.sections.user_demo.list;

import java.util.List;

import javax.inject.Inject;

import app.refineriaweb.com.domain.foundation.UseCase;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoAgent;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;
import rx.Subscriber;

public class GetUsersDemoUseCase extends UseCase<UserDemoAgent> {

    @Inject public GetUsersDemoUseCase(UserDemoAgent agent) {
        super(agent);
    }

    public void getUsers(Subscriber<List<UserDemoEntity>> subscriber) {
        agent.getUsers(subscriber);
    }

}
