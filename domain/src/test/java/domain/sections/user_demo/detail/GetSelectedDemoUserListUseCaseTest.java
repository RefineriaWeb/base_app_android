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

package domain.sections.user_demo.detail;

import org.junit.Test;
import org.mockito.Mock;

import domain.common.BaseTest;
import domain.sections.user_demo.UserDemoRepository;
import domain.sections.user_demo.entities.UserDemoEntity;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class GetSelectedDemoUserListUseCaseTest extends BaseTest {
    private GetSelectedDemoUserListUseCase getSelectedDemoUserListUseCaseUT;
    @Mock protected UserDemoRepository userDemoRepositoryMock;
    @Mock protected UserDemoEntity userDemoEntityMock;

    @Override public void setUp() {
        super.setUp();
        getSelectedDemoUserListUseCaseUT = new GetSelectedDemoUserListUseCase(userDemoRepositoryMock, localeMock, subscribeOnMock, observeOnMock);
    }

    @Test public void When_Execute_Get_User() {
        when(userDemoRepositoryMock.getSelectedUserDemoList()).thenReturn(Observable.just(userDemoEntityMock));

        TestSubscriber<UserDemoEntity> subscriberMock = new TestSubscriber<>();
        getSelectedDemoUserListUseCaseUT.execute(subscriberMock);
        subscriberMock.awaitTerminalEvent();

        assertThat(subscriberMock.getOnCompletedEvents().size(), is(1));
    }
}