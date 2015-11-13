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
import rx.Subscriber;

public class UserDemoAgent extends Agent<UserDemoRepository> {

    @Inject public UserDemoAgent(UserDemoRepository repository, SubscribeOn subscribeOn, ObserveOn observeOn) {
        super(repository, subscribeOn, observeOn);
    }

    public void getUser(String name, Subscriber<domain.sections.user_demo.UserDemoEntity> subscriber) {
        execute(repository.searchByUserName(name), subscriber);
    }

    public void getSelectedDemoUserList(Subscriber<domain.sections.user_demo.UserDemoEntity> subscriber) {
        execute(repository.getSelectedUserDemoList(), subscriber);
    }

    public void getUsers(Subscriber<List<domain.sections.user_demo.UserDemoEntity>> subscriber) {
        execute(repository.askForUsers(), subscriber);
    }

    public void saveSelectedUserDemoList(domain.sections.user_demo.UserDemoEntity user, Subscriber subscriber) {
        execute(repository.saveSelectedUserDemoList(user), subscriber);
    }
}
