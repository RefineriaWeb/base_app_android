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

package domain.sections.user_demo.search;

import org.junit.Test;
import org.mockito.Mock;

import domain.common.BaseTest;
import domain.sections.user_demo.UserDemoRepository;
import domain.sections.user_demo.entities.UserDemoEntity;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class SearchUserDemoUseCaseTest extends BaseTest {
    private SearchUserDemoUseCase searchUserDemoUseCaseUT;
    @Mock protected UserDemoRepository userDemoRepositoryMock;
    @Mock protected UserDemoEntity userDemoEntityMock;

    @Override public void setUp() {
        super.setUp();
        searchUserDemoUseCaseUT = new SearchUserDemoUseCase(userDemoRepositoryMock, subscribeOnMock, observeOnMock, localeMock);
    }

    @Test public void  When_Get_User_With_Valid_Name_Get_User() {
        when(userDemoRepositoryMock.searchByUserName("valid")).thenReturn(Observable.just(userDemoEntityMock));

        TestSubscriber<UserDemoEntity> subscriberMock = new TestSubscriber<>();
        searchUserDemoUseCaseUT.setName("valid");
        searchUserDemoUseCaseUT.execute(subscriberMock);
        subscriberMock.awaitTerminalEvent();

        assertThat(subscriberMock.getOnCompletedEvents().size(), is(1));
    }

    @Test public void  When_Get_User_With_Invalid_Name_Get_Error() {
        when(userDemoRepositoryMock.searchByUserName("invalid")).thenReturn(Observable.create(subscriber -> subscriber.onError(new RuntimeException())));

        TestSubscriber<UserDemoEntity> subscriberMock = new TestSubscriber<>();
        searchUserDemoUseCaseUT.setName("invalid");
        searchUserDemoUseCaseUT.execute(subscriberMock);
        subscriberMock.awaitTerminalEvent();

        assertThat(subscriberMock.getOnErrorEvents().size(), is(1));
    }
}
