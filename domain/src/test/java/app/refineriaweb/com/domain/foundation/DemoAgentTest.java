package app.refineriaweb.com.domain.foundation;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import app.refineriaweb.com.domain.common.BaseTest;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DemoAgentTest extends BaseTest {
    private final String SUCCESS = "success";
    private AgentUnderTest agentUT;

    @Override public void setUp() {
        super.setUp();
        agentUT = new AgentUnderTest();
    }

    @Test public void When_Subscribe_Get_Response() {
        TestSubscriber<String> subscriberMock = new TestSubscriber<>();
        agentUT.execute(subscriberMock);
        subscriberMock.awaitTerminalEvent();

        String response = subscriberMock.getOnNextEvents().get(0);
        assertThat(response, equalToIgnoringCase(SUCCESS));
    }

    @Test public void when_Unsubscribe_Do_Not_Get_Response() {
        TestSubscriber<String> subscriberMock = new TestSubscriber<String>() {
            @Override public void onNext(String ignore) {
                throw new RuntimeException();
            }
        };

        agentUT.execute(subscriberMock);
        try {
            Thread.sleep(100);
            agentUT.dispose();
            Thread.sleep(1000);
            assertThat(subscriberMock.getOnErrorEvents().size(), is(0));
        } catch (InterruptedException e) {e.printStackTrace();}
    }

    private class AgentUnderTest extends Agent<RepositorySuccessMock> {

        public AgentUnderTest() {
            super(new RepositorySuccessMock(), subscribeOnMock, observeOnMock);
        }

        public void execute(Subscriber<String> subscriber) {
            execute(repository.testResponse(), subscriber);
        }
    }

    private class RepositorySuccessMock implements Repository {
        public Observable<String> testResponse() {
            return Observable.just(SUCCESS).delay(1, TimeUnit.SECONDS);
        }
    }
}
