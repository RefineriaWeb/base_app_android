package app.refineriaweb.com.domain.sections.user_demo;

import org.junit.Test;
import org.mockito.Mock;

import app.refineriaweb.com.domain.foundation.BaseTest;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDemoEntityAgentTest extends BaseTest {
    private final static String VALID_USERNAME = "valid", INVALID_USERNAME = "";
    private UserDemoAgent userDemoAgent;
    @Mock private UserDemoRepository userDemoRepository;

    @Override public void setUp() {
        super.setUp();
        userDemoAgent = new UserDemoAgent(userDemoRepository, subscribeOn, observeOn);

        when(userDemoRepository.askForUser(VALID_USERNAME)).thenReturn(Observable.just(mock(UserDemoEntity.class)));
        when(userDemoRepository.askForUser(INVALID_USERNAME)).thenReturn(
                Observable.create(subscriber -> subscriber.onError(new RuntimeException("Not Great")))
        );
    }

    @Test public void when_Get_User_With_Valid_Inputs_Then_Get_A_Response_As_DemoUser() {
        subscribe_To_Login_And_Wait_Until_Observable_Is_Completed(VALID_USERNAME, 0, 1);
    }

    @Test public void when_Login_With_Invalid_Inputs_Then_Throw_An_Exception_On_Subscriber() {
        subscribe_To_Login_And_Wait_Until_Observable_Is_Completed(INVALID_USERNAME, 1, 0);
    }

    private void subscribe_To_Login_And_Wait_Until_Observable_Is_Completed(String username, int expectedErrors, int expectedEvents) {
        TestSubscriber subscriber = new TestSubscriber<>();
        userDemoAgent.getUser(username, subscriber);
        subscriber.awaitTerminalEvent();

        assertThat(subscriber.getOnErrorEvents().size(), is(expectedErrors));
        assertThat(subscriber.getOnNextEvents().size(), is(expectedEvents));
    }

}
