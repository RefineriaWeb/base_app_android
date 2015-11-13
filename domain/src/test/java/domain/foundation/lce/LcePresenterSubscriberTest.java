/*
 * Copyright 2015 RefineriaWeb
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package domain.foundation.lce;

import org.junit.Test;
import org.mockito.Mock;

import domain.common.BaseTest;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

public class LcePresenterSubscriberTest extends BaseTest {
    private domain.foundation.lce.LcePresenterSubscriber presenterSubscriberUT;
    @Mock protected LceView lceViewMock;

    @Override public void setUp() {
        super.setUp();
        presenterSubscriberUT = new domain.foundation.lce.LcePresenterSubscriber(lceViewMock) {};
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
