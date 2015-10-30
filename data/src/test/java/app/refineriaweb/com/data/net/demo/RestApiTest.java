package app.refineriaweb.com.data.net.demo;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import app.refineriaweb.com.data.internal.di.DaggerDataComponent;
import app.refineriaweb.com.data.net.Endpoints;
import app.refineriaweb.com.data.net.RestApi;
import app.refineriaweb.com.domain.entities.UserDemo;
import retrofit.Response;
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
        Endpoints endpoints = DaggerDataComponent.create().endpoints();
        restApi = new RestApi(endpoints);
    }

    @Test public void _1_When_Get_User_With_Valid_User_Name_Then_Get_UserDemo() {
        TestSubscriber<Response<UserDemo>> subscriber = new TestSubscriber<>();
        restApi.askForUser(VALID_USERNAME).subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        Response<UserDemo> userDemo = subscriber.getOnNextEvents().get(0);
        assertThat(userDemo.body().getId(), is(not(0)));
        assertThat(subscriber.getOnNextEvents().size(), is(1));
    }

    @Test public void _2_When_Get_User_With_Invalid_User_Name_Then_Throw_An_ExceptionOnSubscriber() {
        TestSubscriber<Response<UserDemo>> subscriber = new TestSubscriber<>();
        restApi.askForUser(INVALID_USERNAME).subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        Response<UserDemo> userDemo = subscriber.getOnNextEvents().get(0);
        assertThat(userDemo.body(), is(nullValue()));
    }
}
