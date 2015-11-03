package app.refineriaweb.com.data.repositories;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;

import javax.inject.Inject;

import app.refineriaweb.com.data.R;
import app.refineriaweb.com.data.net.RestApi;
import app.refineriaweb.com.data.storage.Persistence;
import app.refineriaweb.com.domain.entities.UserDemo;
import app.refineriaweb.com.domain.repositories.demo.UserDemoRepository;
import retrofit.Response;
import rx.Observable;
import rx.Subscriber;

public class UserDemoDataRepository implements UserDemoRepository {
    private final RestApi restApi;
    private final Persistence persistence;
    private final Context context;

    @Inject public UserDemoDataRepository(Context context, RestApi restApi, Persistence persistence) {
        this.context = context;
        this.restApi = restApi;
        this.persistence = persistence;
    }

    @Override public Observable<UserDemo> askForUser(final String  username) {
        return restApi.askForUser(username).map(response -> {
            handleError(response);

            final UserDemo userDemo = response.body();
            persistence.save(UserDemo.class.getName(), userDemo);
            return userDemo;
        });
    }

    @Override public Observable<UserDemo> askForCachedUser() {
        UserDemo userDemo = persistence.retrieve(UserDemo.class.getName(), UserDemo.class);
        if (userDemo == null) return notCachedUserError();
        return Observable.just(userDemo);
    }

    private <T> Observable<T> notCachedUserError() {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override public void call(Subscriber<? super T> subscriber) {
                subscriber.onError(new RuntimeException("Not cached user"));
            }
        });
    }

    private void handleError(Response response) {
        if (response.isSuccess()) return;

        try {
            ResponseError responseError = new Gson().fromJson(response.errorBody().string(), ResponseError.class);
            throw new RuntimeException(responseError.getMessage());
        } catch (JsonParseException|IOException exception) {
            String message = context.getString(R.string.generic_error);
            throw new RuntimeException(message);
        }
    }

    private static class ResponseError {
        private final String message;

        public ResponseError(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
