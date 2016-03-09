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

package domain.foundation;

import org.junit.Test;

import domain.common.BaseTest;
import domain.foundation.helpers.ParserException;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PresenterErrorsTest extends BaseTest {
    private final String SUCCESS = "success", FAILURE = "failure";
    private PresenterUnderTest presenterUT;
    private UseCaseSuccessMock useCaseSuccessMock;
    private UseCaseFailureMock useCaseFailureMock;

    @Override public void setUp() {
        super.setUp();
        presenterUT = new PresenterUnderTest();
        useCaseSuccessMock = new UseCaseSuccessMock();
        useCaseFailureMock = new UseCaseFailureMock();
    }

    @Test public void When_Subscribe_Safety_Observable_Success_Get_Response() {
        TestSubscriber<String> subscriberMock = new TestSubscriber<>();
        presenterUT.safety(useCaseSuccessMock.react())
                .disposable(oMock -> oMock.subscribe(subscriberMock));
        subscriberMock.awaitTerminalEvent();

        String response = subscriberMock.getOnNextEvents().get(0);
        assertThat(response, is(SUCCESS));
        subscriberMock.assertNoErrors();
    }

    @Test public void When_Subscribe_Safety_Observable_Failure_Nothing_Is_Emitted() {
        Observable<String> oFeedback = Observable.just(FAILURE);
        when(UIMock.errorNonEmptyFields()).thenReturn(oFeedback);

        TestSubscriber<String> subscriberMock = new TestSubscriber<>();
        presenterUT.safety(useCaseFailureMock.react())
                .disposable(oMock -> oMock.subscribe(subscriberMock));
        subscriberMock.awaitTerminalEvent();

        subscriberMock.assertNoErrors();
        subscriberMock.assertNoValues();

        verify(UIMock, times(0)).showFeedback(oFeedback);
    }

    @Test public void When_Subscribe_Safety_Report_Error_Observable_Failure_Nothing_Is_Emitted_And_UI_Show_Error_Is_Called() {
        TestSubscriber<String> subscriberMock = new TestSubscriber<>();
        presenterUT.safetyReportError(useCaseFailureMock.react())
                .disposable(oMock -> oMock.subscribe(subscriberMock));
        subscriberMock.awaitTerminalEvent();

        subscriberMock.assertNoErrors();
        subscriberMock.assertNoValues();

        verify(UIMock, times(1)).showFeedback(any(Observable.class));
    }

    private class PresenterUnderTest extends Presenter<BaseView> {
        final UseCaseSuccessMock useCaseSuccessMock;
        final UseCaseFailureMock useCaseFailureMock;

        public PresenterUnderTest() {
            super(null, subscribeOnMock, observeOnMock, new ParserException(UIMock), UIMock);
            useCaseSuccessMock = new UseCaseSuccessMock();
            useCaseFailureMock = new UseCaseFailureMock();
        }
    }

    private class UseCaseSuccessMock extends UseCase<String> {
        public UseCaseSuccessMock() {
            super(UIMock);
        }

        @Override public Observable<String> react() {
            return Observable.just(SUCCESS);
        }
    }

    private class UseCaseFailureMock extends UseCase<String> {
        public UseCaseFailureMock() {
            super(UIMock);
        }

        @Override public Observable<String> react() {
            return Observable.just(SUCCESS).map(message -> {
                throw new RuntimeException(FAILURE);
            });
        }
    }
}
