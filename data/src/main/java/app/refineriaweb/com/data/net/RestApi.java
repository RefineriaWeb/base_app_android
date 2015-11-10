package app.refineriaweb.com.data.net;

import javax.inject.Inject;

import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;
import retrofit.Response;
import rx.Observable;

public class RestApi {
    private final Endpoints endpoints;

    @Inject public RestApi(Endpoints endpoints) {
        this.endpoints = endpoints;
    }

    public Observable<Response<UserDemoEntity>> askForUser(final String username) {
        return endpoints.getUser(username);
    }
}
