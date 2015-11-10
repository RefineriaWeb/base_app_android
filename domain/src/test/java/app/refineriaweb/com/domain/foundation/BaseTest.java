package app.refineriaweb.com.domain.foundation;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.refineriaweb.com.domain.foundation.schedulers.ObserveOn;
import app.refineriaweb.com.domain.foundation.schedulers.SubscribeOn;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.when;

public abstract class BaseTest {
    protected final static int WAIT = 50;
    @Mock protected ObserveOn observeOn;
    @Mock protected SubscribeOn subscribeOn;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(observeOn.getScheduler()).thenReturn(Schedulers.newThread());
        when(subscribeOn.getScheduler()).thenReturn(Schedulers.newThread());
    }
}