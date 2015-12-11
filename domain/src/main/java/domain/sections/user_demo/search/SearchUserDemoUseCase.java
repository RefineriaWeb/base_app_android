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

package domain.sections.user_demo.search;

import javax.inject.Inject;

import domain.foundation.UseCase;
import domain.foundation.schedulers.ObserveOn;
import domain.foundation.schedulers.SubscribeOn;
import domain.sections.Locale;
import domain.sections.user_demo.UserDemoRepository;
import domain.sections.user_demo.entities.UserDemoEntity;
import rx.Observable;

public class SearchUserDemoUseCase extends UseCase<UserDemoRepository, UserDemoEntity> {
    private String name;

    @Inject public SearchUserDemoUseCase(UserDemoRepository repository, SubscribeOn subscribeOn, ObserveOn observeOn, Locale locale) {
        super(repository, locale, subscribeOn, observeOn);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override protected Observable<UserDemoEntity> buildObservable() {
        assert name != null;
        return repository.searchByUserName(name);
    }
}
