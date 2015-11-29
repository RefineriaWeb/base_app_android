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

package data.user_demo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import data.sections.Locale;
import domain.sections.user_demo.entities.UserDemoEntity;
import data.net.RestApi;
import data.sections.user_demo.UserDemoDataRepository;
import data.storage.Persistence;
import retrofit.Response;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDemoDataRepositoryTest {
    @Mock private RestApi restApi;
    @Mock private Persistence persistence;
    @Mock private Locale locale;
    private UserDemoDataRepository userDemoDataRepository;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
        userDemoDataRepository = new UserDemoDataRepository(restApi, persistence, locale);
    }

    @Test public void When_Search_With_Valid_User_Name_Then_Get_Demo_User() {
        Response<UserDemoEntity> response = Response.success(mock(UserDemoEntity.class));
        when(restApi.getUser(any(String.class))).thenReturn(Observable.just(response));

        TestSubscriber<UserDemoEntity> subscriber = new TestSubscriber<>();
        userDemoDataRepository.searchByUserName(any(String.class)).subscribe(subscriber);
        subscriber.awaitTerminalEvent();

        assertThat(subscriber.getOnNextEvents().size(), is(1));
    }
}