package app.refineriaweb.com.data.net;

import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;
import retrofit.Response;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import rx.Observable;

public interface Endpoints {
    @Headers({ConfigEndpoints.HEADER_API_VERSION})
    @GET("/users/{username}") Observable<Response<UserDemoEntity>> getUser(@Path("username") String username);
}
