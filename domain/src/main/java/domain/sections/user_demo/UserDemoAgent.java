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

package domain.sections.user_demo;

import java.util.List;

import javax.inject.Inject;

import domain.foundation.Agent;
import domain.foundation.schedulers.ObserveOn;
import domain.foundation.schedulers.SubscribeOn;
import domain.sections.Locale;
import domain.sections.user_demo.entities.UserDemoEntity;
import rx.Subscriber;

public class UserDemoAgent extends Agent<UserDemoRepository> {

    @Inject public UserDemoAgent(UserDemoRepository repository, SubscribeOn subscribeOn, ObserveOn observeOn, Locale locale) {
        super(repository, subscribeOn, observeOn, locale);
    }

    public void getUser(String name, Subscriber<UserDemoEntity> subscriber) {
        if (name == null || name.isEmpty())
            executeError(locale.errorNonEmptyFields(), subscriber);
        else
            execute(repository.searchByUserName(name), subscriber);
    }


    public void getSelectedDemoUserList(Subscriber<UserDemoEntity> subscriber) {
        execute(repository.getSelectedUserDemoList(), subscriber);
    }

    public void getUsers(Subscriber<List<UserDemoEntity>> subscriber) {
        execute(repository.askForUsers(), subscriber);
    }

    public void saveSelectedUserDemoList(UserDemoEntity user, Subscriber<Boolean> subscriber) {
        assert user != null;
        execute(repository.saveSelectedUserDemoList(user), subscriber);
    }
}
