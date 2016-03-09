package domain.foundation;

import org.junit.Test;

import domain.common.BaseTest;
import domain.foundation.helpers.ParserException;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 01/02/16.
 */
public class PresenterSubscriptionsTest extends BaseTest {
    private Presenter presenterUT;

    @Override public void setUp() {
        super.setUp();
        presenterUT = new Presenter(null, subscribeOnMock, observeOnMock, new ParserException(UIMock), UIMock) {};
    }

    @Test public void When_Unsubscribe_Subscription_Do_Not_Get_Response() {
        TestSubscriber<Object> subscriberMock = new TestSubscriber();

        presenterUT.safetyReportError(getExpensiveObservable())
                .disposable(observable -> observable.subscribe(subscriberMock));

        presenterUT.dispose();

        waitForIt();
        subscriberMock.assertNoValues();
    }

    @Test public void When_No_Unsubscribe_Subscription_Get_Response() {
        TestSubscriber<Object> subscriberMock = new TestSubscriber();

        presenterUT.safetyReportError(getExpensiveObservable())
                .disposable(observable -> observable.subscribe(subscriberMock));

        waitForIt();
        subscriberMock.assertValueCount(1);
    }

    @Test public void When_No_Unsubscribe_Subscription_With_Several_Subscriptions_Get_Response() {
        TestSubscriber<Object> subscriberMock1 = new TestSubscriber();
        presenterUT.safetyReportError(getExpensiveObservable())
                .disposable(observable -> observable.subscribe(subscriberMock1));

        TestSubscriber<Object> subscriberMock2 = new TestSubscriber();
        presenterUT.safetyReportError(getExpensiveObservable())
                .disposable(observable -> observable.subscribe(subscriberMock2));

        waitForIt();
        subscriberMock1.assertValueCount(1);
        subscriberMock2.assertValueCount(1);
    }

    @Test public void When_Unsubscribe_Subscription_With_Several_Subscriptions_Do_Not_Get_Response() {
        TestSubscriber<Object> subscriberMock1 = new TestSubscriber();
        presenterUT.safetyReportError(getExpensiveObservable())
                .disposable(observable -> observable.subscribe(subscriberMock1));

        TestSubscriber<Object> subscriberMock2 = new TestSubscriber();
        presenterUT.safetyReportError(getExpensiveObservable())
                .disposable(observable -> observable.subscribe(subscriberMock1));

        presenterUT.dispose();

        waitForIt();
        subscriberMock1.assertNoValues();
        subscriberMock2.assertNoValues();
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
