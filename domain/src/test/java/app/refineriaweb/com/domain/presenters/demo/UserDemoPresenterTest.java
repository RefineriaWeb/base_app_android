package app.refineriaweb.com.domain.presenters.demo;

import org.junit.Test;
import org.mockito.Mock;

import app.refineriaweb.com.domain.entities.UserDemo;
import app.refineriaweb.com.domain.interactors.demo.GetCachedUserDemoUseCase;
import app.refineriaweb.com.domain.interactors.demo.GetUserDemoUseCase;
import app.refineriaweb.com.domain.presenters.BasePresenterTest;
import app.refineriaweb.com.domain.repositories.demo.UserDemoRepository;
import app.refineriaweb.com.domain.views.demo.GetUserView;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

public class UserDemoPresenterTest extends BasePresenterTest {
    private final static String A_VALID_USERNAME = "valid", AN_VALID_USERNAME = "";
    @Mock private GetUserView getUserView;

    @Test public void when_Get_User_Success_Then_Show_User_Is_Called() {
        UserDemoPresenter loginPresenter = new UserDemoPresenter(new LoginUseCaseSuccess(), mock(GetCachedUserDemoUseCase.class));
        loginPresenter.attachView(getUserView);
        loginPresenter.getUserByUserName(A_VALID_USERNAME);

        verify(getUserView, timeout(WAIT).times(1)).showUser(any(UserDemo.class));
    }

    @Test public void when_Get_User_Failure_Then_Show_Error_Is_Called() {
        UserDemoPresenter loginPresenter = new UserDemoPresenter(new LoginUseCaseFailure(), mock(GetCachedUserDemoUseCase.class));
        loginPresenter.attachView(getUserView);
        loginPresenter.getUserByUserName(AN_VALID_USERNAME);

        verify(getUserView, timeout(WAIT).times(0)).showUser(any(UserDemo.class));
        verify(getUserView, timeout(WAIT).times(1)).showError(any(String.class));
    }


    private class LoginUseCaseSuccess extends GetUserDemoUseCase {
        public LoginUseCaseSuccess() {
            super(mock(UserDemoRepository.class), subscribeOn, observeOn);
        }

        @Override protected Observable<UserDemo> buildUseCaseObservable() {
            return Observable.just(mock(UserDemo.class));
        }
    }

    private class LoginUseCaseFailure extends GetUserDemoUseCase {
        public LoginUseCaseFailure() {
            super(mock(UserDemoRepository.class), subscribeOn, observeOn);
        }

        @Override protected Observable<UserDemo> buildUseCaseObservable() {
            return Observable.create(subscriber -> subscriber.onError(new RuntimeException(any(String.class))));
        }
    }
}
