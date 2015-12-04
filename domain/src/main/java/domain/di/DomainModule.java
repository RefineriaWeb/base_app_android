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

package domain.di;

import javax.inject.Singleton;

import domain.foundation.schedulers.SubscribeOn;
import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

/**
 * Dagger module for domain layer.
 */
@Module
public class DomainModule {

    /**
     * Provides the Scheduler to be used by any subscriber on subscribing.
     * The operation will be executed in a new thread, this way the UI won't be block
     */
    @Singleton @Provides SubscribeOn provideSubscribeOn() {
        return (() -> Schedulers.io());
    }

}
