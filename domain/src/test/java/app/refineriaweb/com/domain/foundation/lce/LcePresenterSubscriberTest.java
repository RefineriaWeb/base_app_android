package app.refineriaweb.com.domain.foundation.lce;

import org.junit.Test;
import org.mockito.Mock;

import app.refineriaweb.com.domain.common.BaseTest;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

public class LcePresenterSubscriberTest extends BaseTest {
    private LcePresenterSubscriber presenterSubscriberUT;
    @Mock protected LceView lceViewMock;

    @Override public void setUp() {
        super.setUp();
        presenterSubscriberUT = new LcePresenterSubscriber(lceViewMock) {};
    }

    @Test public void when_Observable_Emits_Any_Object_LceView_Reacts_Accordingly() {
        getObservableString().subscribe(presenterSubscriberUT);

        verify(lceViewMock, timeout(WAIT).times(1)).showProgress();
        verify(lceViewMock, timeout(WAIT).times(1)).hideProgress();
        verify(lceViewMock, timeout(WAIT).times(1)).showData(any(String.class));
        verify(lceViewMock, timeout(WAIT).times(0)).showError(any(String.class));
    }

    @Test public void when_Observable_Emits_Exception_LceView_Reacts_Accordingly() {
        getObservableError().subscribe(presenterSubscriberUT);

        verify(lceViewMock, timeout(WAIT).times(1)).showProgress();
        verify(lceViewMock, timeout(WAIT).times(1)).hideProgress();
        verify(lceViewMock, timeout(WAIT).times(0)).showData(any(String.class));
        verify(lceViewMock, timeout(WAIT).times(1)).showError(any(String.class));
    }

    private Observable<String> getObservableString() {
        return Observable.just("")
                .subscribeOn(subscribeOnMock.getScheduler())
                .observeOn(observeOnMock.getScheduler());
    }

    private Observable getObservableError() {
        return Observable.create(subscriber -> subscriber.onError(new RuntimeException("")))
                .subscribeOn(subscribeOnMock.getScheduler())
                .observeOn(observeOnMock.getScheduler());
    }
}
