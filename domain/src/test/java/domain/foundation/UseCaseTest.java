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

import java.util.concurrent.TimeUnit;

import domain.common.BaseTest;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UseCaseTest extends BaseTest {
    private final String SUCCESS = "success";
    private UseCaseUnderTest agentUT;

    @Override public void setUp() {
        super.setUp();
        agentUT = new UseCaseUnderTest();
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

    private class UseCaseUnderTest extends UseCase<RepositorySuccessMock, String> {

        public UseCaseUnderTest() {
            super(new RepositorySuccessMock(), subscribeOnMock, observeOnMock, localeMock);
        }

        @Override protected Observable<String> observable() {
            return repository.testResponse();
        }
    }

    private class RepositorySuccessMock implements domain.foundation.Repository {
        public Observable<String> testResponse() {
            return Observable.just(SUCCESS).delay(1, TimeUnit.SECONDS);
        }
    }
}
