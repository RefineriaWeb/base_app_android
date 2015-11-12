package data.net;

import java.util.List;

import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;
import retrofit.Response;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import rx.Observable;

public interface RestApi {
    String URL_BASE = "https://api.github.com";
    String HEADER_API_VERSION = "Accept: application/vnd.github.v3+json";

    @Headers({HEADER_API_VERSION})
    @GET("/users/{username}") Observable<Response<UserDemoEntity>> getUser(@Path("username") String username);
    @GET("/users") Observable<Response<List<UserDemoEntity>>> getUsers();
}
