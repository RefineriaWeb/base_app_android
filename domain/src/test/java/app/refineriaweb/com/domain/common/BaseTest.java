package app.refineriaweb.com.domain.common;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.refineriaweb.com.domain.foundation.schedulers.ObserveOn;
import app.refineriaweb.com.domain.foundation.schedulers.SubscribeOn;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.when;

public abstract class BaseTest {
    protected final static int WAIT = 50;
    @Mock protected ObserveOn observeOnMock;
    @Mock protected SubscribeOn subscribeOnMock;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(observeOnMock.getScheduler()).thenReturn(Schedulers.newThread());
        when(subscribeOnMock.getScheduler()).thenReturn(Schedulers.newThread());
    }
}