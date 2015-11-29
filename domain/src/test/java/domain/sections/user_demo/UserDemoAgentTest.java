package domain.sections.user_demo;

import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import domain.common.BaseTest;
import domain.sections.user_demo.entities.UserDemoEntity;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class UserDemoAgentTest extends BaseTest {
    private UserDemoAgent userDemoAgentUT;
    @Mock protected UserDemoRepository userDemoRepositoryMock;
    @Mock protected UserDemoEntity userDemoEntityMock;

    @Override public void setUp() {
        super.setUp();
        userDemoAgentUT = new UserDemoAgent(userDemoRepositoryMock, subscribeOnMock, observeOnMock, localeMock);
    }

    @Test public void  When_Get_Users_Get_Users() {
        when(userDemoRepositoryMock.askForUsers()).thenReturn(Observable.just(Arrays.asList()));

        TestSubscriber<List<UserDemoEntity>> subscriberMock = new TestSubscriber<>();
        userDemoAgentUT.getUsers(subscriberMock);
        subscriberMock.awaitTerminalEvent();

        assertThat(subscriberMock.getOnCompletedEvents().size(), is(1));
    }

    @Test public void  When_Get_User_With_Valid_Name_Get_User() {
        when(userDemoRepositoryMock.searchByUserName("valid")).thenReturn(Observable.just(userDemoEntityMock));

        TestSubscriber<UserDemoEntity> subscriberMock = new TestSubscriber<>();
        userDemoAgentUT.getUser("valid", subscriberMock);
        subscriberMock.awaitTerminalEvent();

        assertThat(subscriberMock.getOnCompletedEvents().size(), is(1));
    }

    @Test public void  When_Get_User_With_Invalid_Name_Get_Error() {
        when(userDemoRepositoryMock.searchByUserName("invalid")).thenReturn(Observable.create(subscriber -> subscriber.onError(new RuntimeException())));

        TestSubscriber<UserDemoEntity> subscriberMock = new TestSubscriber<>();
        userDemoAgentUT.getUser("invalid", subscriberMock);
        subscriberMock.awaitTerminalEvent();

        assertThat(subscriberMock.getOnErrorEvents().size(), is(1));
    }
}
