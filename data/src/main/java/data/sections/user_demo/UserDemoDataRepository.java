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

import data.cache.RxProviders;
import data.net.RestApi;
import data.sections.DataRepository;
import data.sections.UI;
import domain.sections.user_demo.UserDemoRepository;
import domain.sections.user_demo.entities.UserDemoEntity;
import io.rx_cache.EvictProvider;
import rx.Observable;

public class UserDemoDataRepository extends DataRepository implements UserDemoRepository {

    @Inject public UserDemoDataRepository(RestApi restApi, RxProviders rxProviders, UI ui) {
        super(restApi, rxProviders, ui);
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

    @Override public Observable<UserDemoEntity> getSelectedUserDemoList() {
        return rxProviders.getSelectedUserDemoList(Observable.just(null), new EvictProvider(false));
    }

    @Override public Observable<Boolean> saveSelectedUserDemoList(UserDemoEntity userSelected) {
        return rxProviders.getSelectedUserDemoList(Observable.just(userSelected), new EvictProvider(true))
                .map(user-> true);
    }
}
