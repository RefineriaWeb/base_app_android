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
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UseCaseTest extends BaseTest {
    private final String SUCCESS = "success", FAILURE = "failure";
    private UseCaseSuccessUnderTest useCaseSuccessUnderTest;
    private UseCaseFailureUnderTest useCaseFailureUnderTest;

    @Override public void setUp() {
        super.setUp();
        useCaseSuccessUnderTest = new UseCaseSuccessUnderTest();
        useCaseFailureUnderTest = new UseCaseFailureUnderTest();
    }

    @Test public void When_Subscribe_Observable_Success_Get_Response() {
        TestSubscriber<String> subscriberMock = new TestSubscriber<>();
        useCaseSuccessUnderTest.observable().subscribe(subscriberMock);
        subscriberMock.awaitTerminalEvent();

        String response = subscriberMock.getOnNextEvents().get(0);
        assertThat(response, is(SUCCESS));
        assertThat(subscriberMock.getOnErrorEvents().size(), is(0));
    }

    @Test public void When_Subscribe_Observable_Failure_Get_Exception() {
        TestSubscriber<String> subscriberMock = new TestSubscriber<>();
        useCaseFailureUnderTest.observable().subscribe(subscriberMock);
        subscriberMock.awaitTerminalEvent();

        String response = subscriberMock.getOnErrorEvents().get(0).getMessage();
        assertThat(response, is(FAILURE));
        assertThat(subscriberMock.getOnNextEvents().size(), is(0));
    }

    @Test public void When_Subscribe_Safety_Observable_Success_Get_Response() {
        TestSubscriber<String> subscriberMock = new TestSubscriber<>();
        useCaseSuccessUnderTest.observable().subscribe(subscriberMock);
        subscriberMock.awaitTerminalEvent();

        String response = subscriberMock.getOnNextEvents().get(0);
        assertThat(response, is(SUCCESS));
        assertThat(subscriberMock.getOnErrorEvents().size(), is(0));
    }

    @Test public void When_Subscribe_Safety_Observable_Failure_Nothing_Is_Emitted() {
        when(UIMock.errorNonEmptyFields()).thenReturn(FAILURE);

        TestSubscriber<String> subscriberMock = new TestSubscriber<>();
        useCaseFailureUnderTest.safetyObservable().subscribe(subscriberMock);
        subscriberMock.awaitTerminalEvent();

        assertThat(subscriberMock.getOnErrorEvents().size(), is(0));
        assertThat(subscriberMock.getOnNextEvents().size(), is(0));

        verify(UIMock, times(0)).showFeedback(FAILURE);
    }

    @Test public void When_Subscribe_Safety_Report_Error_Observable_Failure_Nothing_Is_Emitted_And_UI_Show_Error_Is_Called() {
        when(UIMock.errorNonEmptyFields()).thenReturn(FAILURE);

        TestSubscriber<String> subscriberMock = new TestSubscriber<>();
        useCaseFailureUnderTest.safetyReportErrorObservable().subscribe(subscriberMock);
        subscriberMock.awaitTerminalEvent();

        assertThat(subscriberMock.getOnErrorEvents().size(), is(0));
        assertThat(subscriberMock.getOnNextEvents().size(), is(0));

        verify(UIMock, times(1)).showFeedback(FAILURE);
    }

    private class UseCaseSuccessUnderTest extends UseCase<String> {
        public UseCaseSuccessUnderTest() {
            super(UIMock, subscribeOnMock, observeOnMock);
        }

        @Override public Observable<String> builtObservable() {
            return Observable.just(SUCCESS);
        }
    }

    private class UseCaseFailureUnderTest extends UseCase<String> {
        public UseCaseFailureUnderTest() {
            super(UIMock, subscribeOnMock, observeOnMock);
        }

        @Override public Observable<String> builtObservable() {
            return Observable.just(SUCCESS).map(message -> {
                throw new RuntimeException(FAILURE);
            });
        }
    }
}
