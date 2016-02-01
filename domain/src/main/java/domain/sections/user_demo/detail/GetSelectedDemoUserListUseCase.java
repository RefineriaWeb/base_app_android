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

package domain.sections.user_demo.detail;

import javax.inject.Inject;

import domain.foundation.UseCase;
import domain.foundation.schedulers.ObserveOn;
import domain.foundation.schedulers.SubscribeOn;
import domain.sections.UI;
import domain.sections.user_demo.UserDemoRepository;
import domain.sections.user_demo.entities.UserDemoEntity;
import rx.Observable;

public class GetSelectedDemoUserListUseCase extends UseCase<UserDemoEntity> {
    private final UserDemoRepository repository;

    @Inject GetSelectedDemoUserListUseCase(UserDemoRepository repository, UI ui, SubscribeOn subscribeOn, ObserveOn observeOn) {
        super(ui, subscribeOn, observeOn);
        this.repository = repository;
    }

    @Override public Observable<UserDemoEntity> builtObservable() {
        return repository.getSelectedUserDemoList();
    }
}
