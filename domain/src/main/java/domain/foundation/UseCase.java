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
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Base class for any UseCase.
 * The UseCase retrieves its data from an specific repository and
 * it is in charge of processing data for any presenter.
 * @param <R> The repository interface used by this use case
 * @see  Repository
 */

public abstract class UseCase<R extends Repository, D> implements Disposable {
    protected final R repository;
    protected final Locale locale;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;
    private Subscription subscription = Subscriptions.empty();

    public UseCase(R repository, Locale locale, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.repository = repository;
        this.locale = locale;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    public void execute(Subscriber<D> subscriber) {
        assert subscriber != null;

        unsubscribe();

        subscription = buildObservable()
                .subscribeOn(subscribeOn.getScheduler())
                .observeOn(observeOn.getScheduler())
                .subscribe(subscriber);
    }

    /**
     * Retrieve the built observable without executing it,
     * provided to use use case inside another
     */
    public Observable<D> getObservable() {
        return buildObservable();
    }

    protected abstract Observable<D> buildObservable();

    private void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override public void dispose() {
        unsubscribe();
    }
}