package app.refineriaweb.com.domain.sections.user_demo.list;

import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import app.refineriaweb.com.domain.common.BaseTest;
import app.refineriaweb.com.domain.sections.Wireframe;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoAgent;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoRepository;
import rx.Observable;
import rx.Subscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

public class UsersDemoPresenterTest extends BaseTest {
    @Mock private Wireframe wireframe;
    @Mock private UsersView usersView;

    @Test public void when_Get_Users_Then_Show_Users_Is_Called() {
        GetUsersDemoUseCase useCase = new GetUsersDemoUseCase(new GetUserSuccess());

        UsersDemoPresenter loginPresenter = new UsersDemoPresenter(wireframe, useCase);
        loginPresenter.attachView(usersView);

        verify(usersView, timeout(WAIT).times(1)).showData(any(List.class));
    }

    @Test public void when_Get_Users_Failure_Then_Show_Error_Is_Called() {
        GetUsersDemoUseCase useCase = new GetUsersDemoUseCase(new GetUserFailure());

        UsersDemoPresenter loginPresenter = new UsersDemoPresenter(wireframe, useCase);
        loginPresenter.attachView(usersView);

        verify(usersView, timeout(WAIT).times(0)).showData(any(List.class));
        verify(usersView, timeout(WAIT).times(1)).showError(any(String.class));
    }

    private class GetUserSuccess extends UserDemoAgent {
        public GetUserSuccess() {
            super(mock(UserDemoRepository.class), subscribeOn, observeOn);
        }

        @Override public void execute(Observable ignore, Subscriber subscriber) {
            Observable<List<UserDemoEntity>> observable = Observable.just(Arrays.asList(mock(UserDemoEntity.class)));
            super.execute(observable, subscriber);
        }
    }

    private class GetUserFailure extends UserDemoAgent {
        public GetUserFailure() {
            super(mock(UserDemoRepository.class), subscribeOn, observeOn);
        }

        @Override public void execute(Observable ignore, Subscriber subscriber) {
            super.execute(Observable.create(subscriberObservable -> subscriberObservable.onError(new RuntimeException(any(String.class)))), subscriber);

        }
    }
}
