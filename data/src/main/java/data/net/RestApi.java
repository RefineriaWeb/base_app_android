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
