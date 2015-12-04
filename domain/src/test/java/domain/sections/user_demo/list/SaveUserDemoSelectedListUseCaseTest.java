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

package domain.sections.user_demo.list;

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

public class SaveUserDemoSelectedListUseCaseTest extends BaseTest {
    private SaveUserDemoSelectedListUseCase saveUserDemoSelectedListUseCaseUT;
    @Mock protected UserDemoRepository userDemoRepositoryMock;
    @Mock protected UserDemoEntity userDemoEntityMock;

    @Override public void setUp() {
        super.setUp();
        saveUserDemoSelectedListUseCaseUT = new SaveUserDemoSelectedListUseCase(userDemoRepositoryMock, subscribeOnMock, observeOnMock, localeMock);
    }

    @Test public void When_Save_User_Then_Get_Boolean_Observable() {
        when(userDemoRepositoryMock.saveSelectedUserDemoList(userDemoEntityMock)).thenReturn(Observable.just(true));

        saveUserDemoSelectedListUseCaseUT.setUserDemoEntity(userDemoEntityMock);

        TestSubscriber<Boolean> subscriberMock = new TestSubscriber<>();
        saveUserDemoSelectedListUseCaseUT.execute(subscriberMock);
        subscriberMock.awaitTerminalEvent();

        assertThat(subscriberMock.getOnCompletedEvents().size(), is(1));
    }

    @Test(expected=AssertionError.class)
    public void When_Save_Null_Then_Throws_Assertion_Error() {
        TestSubscriber<Boolean> subscriberMock = new TestSubscriber<>();
        saveUserDemoSelectedListUseCaseUT.execute(subscriberMock);
        subscriberMock.awaitTerminalEvent();
    }
}
