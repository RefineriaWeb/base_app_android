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

package domain.foundation;

import domain.foundation.schedulers.ObserveOn;
import domain.foundation.schedulers.SubscribeOn;
import domain.sections.Locale;

/**
 * Base class for any UseCase.
 * UseCase which retrieves its data from an specific repository
 * @param <R> The repository interface used by this agent
 * @see  Repository
 */

public abstract class UseCaseSingleRepository<R extends Repository, D> extends UseCase<D> implements Disposable {
    protected final R repository;

    public UseCaseSingleRepository(R repository, SubscribeOn subscribeOn, ObserveOn observeOn, Locale locale) {
        super(subscribeOn, observeOn, locale);
        this.repository = repository;
    }
}

