package app.refineriaweb.com.data.sesions.user_demo;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;

import javax.inject.Inject;

import app.refineriaweb.com.data.R;
import app.refineriaweb.com.data.net.RestApi;
import app.refineriaweb.com.data.storage.Persistence;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoRepository;
import retrofit.Response;
import rx.Observable;

public class UserDemoDataRepository implements UserDemoRepository {
    private final RestApi restApi;
    private final Persistence persistence;
    private final Context context;

    @Inject public UserDemoDataRepository(Context context, RestApi restApi, Persistence persistence) {
        this.context = context;
        this.restApi = restApi;
        this.persistence = persistence;
    }

    @Override public Observable<UserDemoEntity> askForUser(final String  username) {
        return restApi.askForUser(username).map(response -> {
            handleError(response);

            final UserDemoEntity user = response.body();
            persistence.save(UserDemoEntity.class.getName(), user);
            return user;
        });
    }

    @Override public Observable<UserDemoEntity> askForCachedUser() {
        UserDemoEntity userDemoEntity = persistence.retrieve(UserDemoEntity.class.getName(), UserDemoEntity.class);
        if (userDemoEntity == null) return notCachedUserError();
        return Observable.just(userDemoEntity);
    }

    private <T> Observable<T> notCachedUserError() {
        return Observable.create(subscriber -> subscriber.onError(new RuntimeException("Not cached user")));
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
