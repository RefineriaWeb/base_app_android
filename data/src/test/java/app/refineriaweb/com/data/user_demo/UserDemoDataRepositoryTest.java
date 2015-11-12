package app.refineriaweb.com.data.user_demo;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.refineriaweb.com.data.net.RestApi;
import app.refineriaweb.com.data.sesions.user_demo.UserDemoDataRepository;
import app.refineriaweb.com.data.storage.Persistence;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;
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
    @Mock private Context context;
    private UserDemoDataRepository userDemoDataRepository;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
        userDemoDataRepository = new UserDemoDataRepository(context, restApi, persistence);
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