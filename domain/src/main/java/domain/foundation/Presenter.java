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

import domain.foundation.helpers.ParserException;
import domain.foundation.schedulers.ObserveOn;
import domain.foundation.schedulers.SubscribeOn;
import domain.sections.UI;
import domain.sections.Wireframe;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Base class for any Presenter.
 * The presenter is responsible for linking the uses cases in order to create a logical unit
 * which will be represented as a screen in the application.
 * @param <V> The view interface attached to this presenter.
 * @see  BaseView
 */
public abstract class Presenter<V extends BaseView> {
    protected V view;
    protected final Wireframe wireframe;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;
    private final ParserException parserException;
    protected final UI ui;
    private final CompositeSubscription subscriptions;

    public Presenter(Wireframe wireframe, SubscribeOn subscribeOn, ObserveOn observeOn, ParserException parserException, UI ui) {
        this.wireframe = wireframe;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
        this.parserException = parserException;
        this.ui = ui;
        this.subscriptions = new CompositeSubscription();
    }

    /**
     * Called when view is required to initialize. On the Android lifecycle ecosystem, it would be onCreated.
     */
    public void attachView(V view) {
        this.view = view;
    }

    /**
     * Called when view is required to resume. On the Android lifecycle ecosystem, it would be onResume.
     */
    public void resumeView() {}

    /**
     * Handles observable subscriptions, not throw any exception and report it using feedback.
     */
    protected <T> Disposing<T> safetyReportError(Observable<T> observable) {
        Observable<T> configured = schedulers(observable)
                .doOnError(throwable -> ui.showFeedback(parserException.with(throwable).react()))
                .onErrorResumeNext(throwable -> Observable.empty());

        return new Disposing(configured, subscriptions);
    }

    /**
     * Handles observable subscriptions, not throw any exception and report it using anchoredScreenFeedback.
     */
    protected <T> Disposing<T> safetyReportErrorAnchored(Observable<T> observable) {
        Observable<T> configured = schedulers(observable)
                .doOnError(throwable -> ui.showAnchoredScreenFeedback(parserException.with(throwable).react()))
                .onErrorResumeNext(throwable -> Observable.empty());

        return new Disposing(configured, subscriptions);
    }

    /**
     * Handles observable subscriptions and not throw any exception.
     */
    protected <T> Disposing<T> safety(Observable<T> observable) {
        Observable<T> configured = schedulers(observable)
                .onErrorResumeNext(throwable -> Observable.empty());

        return new Disposing(configured, subscriptions);
    }

    /**
     * Handles observable schedulers.
     */
    private  <T> Observable<T> schedulers(Observable<T> observable) {
        return observable.subscribeOn(subscribeOn.getScheduler())
                .observeOn(observeOn.getScheduler());
    }

    public void dispose() {
        if (!subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }

    /**
     * Wrapper to hold a reference to the observable and the expected subscription.
     */
    protected static class Disposing<T> {
        private Observable<T> observable;
        private final CompositeSubscription subscriptions;

        private Disposing(Observable<T> observable, CompositeSubscription subscriptions) {
            this.observable = observable;
            this.subscriptions = subscriptions;
        }

        public void disposable(Disposable<T> disposable) {
            subscriptions.add(disposable.subscription(observable));
        }

        public interface Disposable<T> {
            Subscription subscription(Observable<T> observable);
        }
    }
}
