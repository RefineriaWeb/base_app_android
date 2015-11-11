package app.refineriaweb.com.domain.sections.user_demo.detail;

import org.junit.Test;
import org.mockito.Mock;

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

public class UserDemoPresenterTest extends BaseTest {
    private final static String A_VALID_USERNAME = "valid", AN_VALID_USERNAME = "";
    @Mock private UserView userView;
    @Mock private Wireframe wireframe;

    @Test public void when_Get_User_Success_Then_Show_User_Is_Called() {
        GetUserDemoUseCase useCase = new GetUserDemoUseCase(new LoginAgentSuccess());

        UserDemoPresenter loginPresenter = new UserDemoPresenter(wireframe, useCase, mock(GetSelectedDemoUserListUseCase.class));
        loginPresenter.attachView(userView);
        loginPresenter.getUserByUserName(A_VALID_USERNAME);

        verify(userView, timeout(WAIT).times(1)).showData(any(UserDemoEntity.class));
    }

    @Test public void when_Get_User_Failure_Then_Show_Error_Is_Called() {
        GetUserDemoUseCase useCase = new GetUserDemoUseCase(new LoginAgentFailure());

        UserDemoPresenter loginPresenter = new UserDemoPresenter(wireframe, useCase, mock(GetSelectedDemoUserListUseCase.class));
        loginPresenter.attachView(userView);
        loginPresenter.getUserByUserName(AN_VALID_USERNAME);

        verify(userView, timeout(WAIT).times(0)).showData(any(UserDemoEntity.class));
        verify(userView, timeout(WAIT).times(1)).showError(any(String.class));
    }


    private class LoginAgentSuccess extends UserDemoAgent {
        public LoginAgentSuccess() {
            super(mock(UserDemoRepository.class), subscribeOn, observeOn);
        }

        @Override public void execute(Observable observable, Subscriber subscriber) {
            super.execute(Observable.just(mock(UserDemoEntity.class)), subscriber);
        }
    }

    private class LoginAgentFailure extends UserDemoAgent {
        public LoginAgentFailure() {
            super(mock(UserDemoRepository.class), subscribeOn, observeOn);
        }

        @Override public void execute(Observable observable, Subscriber subscriber) {
            super.execute(Observable.create(subscriberObservable -> subscriberObservable.onError(new RuntimeException(any(String.class)))), subscriber);
        }
    }
}
