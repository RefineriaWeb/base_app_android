package domain.foundation;

import org.junit.Test;

import domain.common.BaseTest;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by victor on 01/02/16.
 */
public class PresenterTest extends BaseTest {
    private Presenter presenterUT;

    @Override public void setUp() {
        super.setUp();
        presenterUT = new Presenter(null, subscribeOn, observeOn, parseException, UIMock) {};
    }

    @Test public void When_Unsubscribe_Subscription_Do_Not_Get_Response() {
        TestSubscriber<Object> subscriberMock = new TestSubscriber();

        presenterUT.subscriptions(getExpensiveObservable().subscribe(subscriberMock));
        presenterUT.dispose();

        waitForIt();
        assertThat(subscriberMock.getOnNextEvents().size(), is(0));
    }

    @Test public void When_No_Unsubscribe_Subscription_Get_Response() {
        TestSubscriber<Object> subscriberMock = new TestSubscriber();

        presenterUT.subscriptions(getExpensiveObservable().subscribe(subscriberMock));

        waitForIt();
        assertThat(subscriberMock.getOnNextEvents().size(), is(1));
    }

    @Test public void When_No_Unsubscribe_Subscription_With_Several_Subscriptions_Get_Response() {
        TestSubscriber<Object> subscriberMock1 = new TestSubscriber();
        presenterUT.subscriptions(getExpensiveObservable().subscribe(subscriberMock1));

        TestSubscriber<Object> subscriberMock2 = new TestSubscriber();
        presenterUT.subscriptions(getExpensiveObservable().subscribe(subscriberMock2));

        waitForIt();
        assertThat(subscriberMock1.getOnNextEvents().size(), is(1));
        assertThat(subscriberMock2.getOnNextEvents().size(), is(1));
    }

    @Test public void When_Unsubscribe_Composite_Subscription_Do_Not_Get_Response() {
        CompositeSubscription compositeSubscription = new CompositeSubscription();

        TestSubscriber<Object> subscriberMock1 = new TestSubscriber();
        TestSubscriber<Object> subscriberMock2 = new TestSubscriber();

        compositeSubscription.add(getExpensiveObservable().subscribe(subscriberMock1));
        compositeSubscription.add(getExpensiveObservable().subscribe(subscriberMock2));

        presenterUT.subscriptions(compositeSubscription);
        presenterUT.dispose();

        waitForIt();
        assertThat(subscriberMock1.getOnNextEvents().size(), is(0));
        assertThat(subscriberMock2.getOnNextEvents().size(), is(0));
    }

    @Test public void When_No_Unsubscribe_Composite_Subscription_Get_Response() {
        CompositeSubscription compositeSubscription = new CompositeSubscription();

        TestSubscriber<Object> subscriberMock1 = new TestSubscriber();
        TestSubscriber<Object> subscriberMock2 = new TestSubscriber();

        compositeSubscription.add(getExpensiveObservable().subscribe(subscriberMock1));
        compositeSubscription.add(getExpensiveObservable().subscribe(subscriberMock2));

        presenterUT.subscriptions(compositeSubscription);

        waitForIt();
        assertThat(subscriberMock1.getOnNextEvents().size(), is(1));
        assertThat(subscriberMock2.getOnNextEvents().size(), is(1));
    }

    private Observable<Object> getExpensiveObservable() {
        return Observable.create(subscriber -> {
            try {
                Thread.sleep(500);
                subscriber.onNext("Mock");
            } catch (InterruptedException e) {}
        }).subscribeOn(Schedulers.newThread());
    }

    private void waitForIt() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
