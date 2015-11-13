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

package app.refineriaweb.com.domain.foundation;

import app.refineriaweb.com.domain.foundation.schedulers.ObserveOn;
import app.refineriaweb.com.domain.foundation.schedulers.SubscribeOn;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class Agent<R extends Repository> implements Disposable {
    protected final R repository;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;
    private Subscription subscription = Subscriptions.empty();

    public Agent(R repository, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.repository = repository;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    protected <T> void execute(Observable<T> observable, Subscriber<T> subscriber) {
        subscription.unsubscribe();

        subscription = observable
                .subscribeOn(subscribeOn.getScheduler())
                .observeOn(observeOn.getScheduler())
                .subscribe(subscriber);
    }

    private void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override public void dispose() {
        unsubscribe();
    }
}
