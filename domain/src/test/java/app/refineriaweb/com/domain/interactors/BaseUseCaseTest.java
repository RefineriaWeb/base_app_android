package app.refineriaweb.com.domain.interactors;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.refineriaweb.com.domain.schedulers.ObserveOn;
import app.refineriaweb.com.domain.schedulers.SubscribeOn;
import rx.schedulers.Schedulers;


import static org.mockito.Mockito.when;

public abstract class BaseUseCaseTest {
    @Mock protected ObserveOn observeOn;
    @Mock protected SubscribeOn subscribeOn;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(observeOn.getScheduler()).thenReturn(Schedulers.newThread());
        when(subscribeOn.getScheduler()).thenReturn(Schedulers.newThread());
    }
}