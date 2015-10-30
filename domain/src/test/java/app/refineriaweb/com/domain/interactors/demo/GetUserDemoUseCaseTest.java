package app.refineriaweb.com.domain.interactors.demo;

import org.junit.Test;
import org.mockito.Mock;

import app.refineriaweb.com.domain.entities.UserDemo;
import app.refineriaweb.com.domain.interactors.BaseUseCaseTest;
import app.refineriaweb.com.domain.repositories.demo.UserDemoRepository;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetUserDemoUseCaseTest extends BaseUseCaseTest {
    private final static String A_VALID_USERNAME = "valid", AN_VALID_USERNAME = "";
    private GetUserDemoUseCase getUserDemoUseCase;
    @Mock private UserDemoRepository userDemoRepository;

    @Override public void setUp() {
        super.setUp();
        getUserDemoUseCase = new GetUserDemoUseCase(userDemoRepository, subscribeOn, observeOn);

        when(userDemoRepository.askForUser(A_VALID_USERNAME)).thenReturn(Observable.just(mock(UserDemo.class)));
        when(userDemoRepository.askForUser(AN_VALID_USERNAME)).thenReturn(Observable.create(
                        new Observable.OnSubscribe<UserDemo>() {
                            @Override public void call(Subscriber<? super UserDemo> subscriber) {
                                subscriber.onError(new RuntimeException("Not Great"));
                            }
                        })
        );
    }

    @Test public void when_Get_User_With_Valid_Inputs_Then_Get_A_Response_As_DemoUser() {
        getUserDemoUseCase.setUserName(A_VALID_USERNAME);
        getUserDemoUseCase.buildUseCaseObservable();
        subscribe_To_Login_And_Wait_Until_Observable_Is_Completed(0, 1);
    }

    @Test public void when_Login_With_Invalid_Inputs_Then_Throw_An_Exception_On_Subscriber() {
        getUserDemoUseCase.setUserName(AN_VALID_USERNAME);
        getUserDemoUseCase.buildUseCaseObservable();
        subscribe_To_Login_And_Wait_Until_Observable_Is_Completed(1, 0);
    }

    private void subscribe_To_Login_And_Wait_Until_Observable_Is_Completed(int expectedErrors, int expectedEvents) {
        TestSubscriber<UserDemo> subscriber = new TestSubscriber<>();
        getUserDemoUseCase.execute(subscriber);
        subscriber.awaitTerminalEvent();

        assertThat(subscriber.getOnErrorEvents().size(), is(expectedErrors));
        assertThat(subscriber.getOnNextEvents().size(), is(expectedEvents));
    }

}
