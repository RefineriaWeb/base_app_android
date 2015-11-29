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

package data.net;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import domain.sections.user_demo.entities.UserDemoEntity;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.observers.TestSubscriber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestApiTest {
    private static final String VALID_USERNAME = "RefineriaWeb", INVALID_USERNAME = "";

    private RestApi restApi;

    @Before public void setUp() {
        restApi = new Retrofit.Builder()
                .baseUrl(RestApi.URL_BASE)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RestApi.class);
    }

    @Test public void _1_When_Get_User_With_Valid_User_Name_Then_Get_UserDemo() {
        TestSubscriber<Response<UserDemoEntity>> subscriber = new TestSubscriber<>();
        restApi.getUser(VALID_USERNAME).subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        Response<UserDemoEntity> userDemo = subscriber.getOnNextEvents().get(0);
        assertThat(userDemo.body().getId(), is(not(0)));
        assertThat(subscriber.getOnNextEvents().size(), is(1));
    }

    @Test public void _2_When_Get_User_With_Invalid_User_Name_Then_Throw_An_ExceptionOnSubscriber() {
        TestSubscriber<Response<UserDemoEntity>> subscriber = new TestSubscriber<>();
        restApi.getUser(INVALID_USERNAME).subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        Response<UserDemoEntity> userDemo = subscriber.getOnNextEvents().get(0);
        assertThat(userDemo.body(), is(nullValue()));
    }

    @Test public void _3_When_Get_Users_Then_Get_Users() {
        TestSubscriber<Response<List<UserDemoEntity>>> subscriber = new TestSubscriber<>();
        restApi.getUsers().subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        assertThat(subscriber.getOnNextEvents().size(), is(not(0)));
    }
}
