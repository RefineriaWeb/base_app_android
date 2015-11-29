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


import java.util.List;

import javax.inject.Inject;

import data.net.RestApi;
import data.sections.DataRepository;
import data.sections.Locale;
import data.storage.Persistence;
import domain.sections.user_demo.entities.UserDemoEntity;
import domain.sections.user_demo.UserDemoRepository;
import rx.Observable;

public class UserDemoDataRepository extends DataRepository implements UserDemoRepository {

    @Inject public UserDemoDataRepository(RestApi restApi, Persistence persistence, Locale locale) {
        super(restApi, persistence, locale);
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

    @Override public Observable<Boolean> saveSelectedUserDemoList(UserDemoEntity user) {
        boolean success = persistence.save(UserDemoEntity.class.getName(), user);
        if (success) return buildObservable(success);
        else return buildObservableError(locale.genericError());
    }

    @Override public Observable<UserDemoEntity> getSelectedUserDemoList() {
        UserDemoEntity userDemoEntity = persistence.retrieve(UserDemoEntity.class.getName(), UserDemoEntity.class);
        if (userDemoEntity != null) return buildObservable(userDemoEntity);
        else return buildObservableError(locale.genericError());
    }

}
