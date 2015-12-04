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

package domain.sections.user_demo.list;

import java.util.List;

import javax.inject.Inject;

import domain.foundation.UseCaseSingleRepository;
import domain.foundation.schedulers.ObserveOn;
import domain.foundation.schedulers.SubscribeOn;
import domain.sections.Locale;
import domain.sections.user_demo.UserDemoRepository;
import domain.sections.user_demo.entities.UserDemoEntity;
import rx.Observable;

public class GetUsersDemoUseCase extends UseCaseSingleRepository<UserDemoRepository, List<UserDemoEntity>> {

    @Inject public GetUsersDemoUseCase(UserDemoRepository repository, SubscribeOn subscribeOn, ObserveOn observeOn, Locale locale) {
        super(repository, subscribeOn, observeOn, locale);
    }

    @Override protected Observable<List<UserDemoEntity>> buildObservable() {
        return repository.askForUsers();
    }
}
