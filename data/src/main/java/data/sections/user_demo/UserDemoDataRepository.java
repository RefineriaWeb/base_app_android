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

package data.sections.user_demo;


import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import domain.sections.user_demo.UserDemoEntity;
import domain.sections.user_demo.UserDemoRepository;
import data.net.RestApi;
import data.storage.Persistence;
import retrofit.Response;
import rx.Observable;

public class UserDemoDataRepository implements UserDemoRepository {
    private final RestApi restApi;
    private final Persistence persistence;

    @Inject public UserDemoDataRepository(RestApi restApi, Persistence persistence) {
        this.restApi = restApi;
        this.persistence = persistence;
    }

    @Override public Observable<UserDemoEntity> searchByUserName(final String  username) {
        return restApi.getUser(username).map(response -> {
            handleError(response);
            final UserDemoEntity user = response.body();
            return user;
        });
    }

    @Override public Observable<List<UserDemoEntity>> askForUsers() {
        return restApi.getUsers().map(response -> {
            handleError(response);
            return response.body();
        });
    }

    @Override public Observable saveSelectedUserDemoList(UserDemoEntity user) {
        return Observable.create(subscriber -> {
            boolean success = persistence.save(UserDemoEntity.class.getName(), user);
            if (success) subscriber.onCompleted();
            else subscriber.onError(new RuntimeException("Can't saved user"));
        });
    }

    @Override public Observable<UserDemoEntity> getSelectedUserDemoList() {
        return Observable.create(subscriber -> {
            UserDemoEntity userDemoEntity = persistence.retrieve(UserDemoEntity.class.getName(), UserDemoEntity.class);
            if (userDemoEntity != null) subscriber.onNext(userDemoEntity);
            else subscriber.onError(new RuntimeException("Can't get user"));
        });
    }

    private void handleError(Response response) {
        if (response.isSuccess()) return;

        try {
            ResponseError responseError = new Gson().fromJson(response.errorBody().string(), ResponseError.class);
            throw new RuntimeException(responseError.getMessage());
        } catch (JsonParseException|IOException exception) {
            throw new RuntimeException("Generic error");
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
