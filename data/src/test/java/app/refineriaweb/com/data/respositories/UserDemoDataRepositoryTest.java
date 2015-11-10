package app.refineriaweb.com.data.respositories;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.refineriaweb.com.data.net.RestApi;
import app.refineriaweb.com.data.repositories.UserDemoDataRepository;
import app.refineriaweb.com.data.storage.Persistence;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;
import retrofit.Response;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
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

    @Test public void when_Get_User_With_Valid_User_Name_Then_Get_Demo_User_And_Saved_It() {
        Response<UserDemoEntity> response = Response.success(null);
        when(restApi.askForUser(any(String.class))).thenReturn(Observable.just(response));

        TestSubscriber<UserDemoEntity> subscriber = new TestSubscriber<>();
        userDemoDataRepository.askForUser(any(String.class)).subscribe(subscriber);
        subscriber.awaitTerminalEvent();

        assertThat(subscriber.getOnNextEvents().size(), is(1));
        verify(persistence).save(any(String.class), any(Object.class));
    }

    @Test public void when_Login_With_Invalid_Inputs_Then_Credentials_Is_Not_Saved() {
        when(restApi.askForUser(any(String.class))).thenReturn(Observable.create(
                        new Observable.OnSubscribe<Response<UserDemoEntity>>() {
                            @Override  public void call(Subscriber<? super Response<UserDemoEntity>> subscriber) {
                                subscriber.onError(new RuntimeException("Not Great"));
                            }
                        })
        );

        TestSubscriber<UserDemoEntity> subscriber = new TestSubscriber<>();
        userDemoDataRepository.askForUser(any(String.class)).subscribe(subscriber);
        subscriber.awaitTerminalEvent();

        assertThat(subscriber.getOnErrorEvents().size(), is(1));
        verifyZeroInteractions(persistence);
    }
}