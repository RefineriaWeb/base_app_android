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

package domain.sections.launch;

import javax.inject.Inject;

import domain.foundation.Repository;
import domain.foundation.UseCase;
import domain.foundation.schedulers.ObserveOn;
import domain.foundation.schedulers.SubscribeOn;
import domain.sections.Locale;
import rx.Observable;


public class LaunchUseCase extends UseCase<LaunchUseCase.UserRepository, Boolean> {

    @Inject LaunchUseCase(UserDataRepository repository, Locale locale, SubscribeOn subscribeOn, ObserveOn observeOn) {
        super(repository, locale, subscribeOn, observeOn);
    }

    /**
     * Do some useful validation.  Is user logged?, etc
     */
    @Override protected Observable<Boolean> buildObservable() {
        return repository.isUserLogged();
    }

    interface UserRepository extends Repository {
        Observable<Boolean> isUserLogged();
    }

    static class UserDataRepository implements UserRepository {

        @Inject UserDataRepository() {}

        @Override public Observable<Boolean> isUserLogged() {
            return null;
        }
    }

}
