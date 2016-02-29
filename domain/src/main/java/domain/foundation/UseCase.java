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
import domain.sections.UI;
import rx.Observable;
import rx.exceptions.CompositeException;

/**
 * Base class for any UseCase.
 * The UseCase retrieves its data from an specific repository and
 * it is in charge of processing data for any presenter.
 * @see  Repository
 */

public abstract class UseCase<D> {
    protected final UI ui;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;

    public UseCase(UI ui, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.ui = ui;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    public Observable<D> safetyObservable() {
        return observable().onErrorResumeNext(throwable -> Observable.empty());
    }

    public Observable<D> safetyReportErrorObservable() {
        return observable()
                .doOnError(throwable -> ui.showFeedback(getInfoFromException(throwable)))
                .onErrorResumeNext(throwable -> Observable.empty());
    }

    public Observable<D> safetyReportErrorAnchoredObservable() {
        return observable()
                .doOnError(throwable -> ui.showAnchoredScreenFeedback(getInfoFromException(throwable)))
                .onErrorResumeNext(throwable -> Observable.empty());
    }

    private String getInfoFromException(Throwable throwable) {
        String message = throwable.getMessage();

        if (throwable instanceof CompositeException) {
            message += System.getProperty("line.separator");
            CompositeException compositeException = (CompositeException) throwable;

            for (Throwable exception : compositeException.getExceptions()) {
                String exceptionName = exception.getClass().getSimpleName();
                String exceptionMessage = exception.getMessage() != null ? exception.getMessage() : "";
                message += exceptionName + " -> " + exceptionMessage + System.getProperty("line.separator");
            }
        }

        return message;
    }

    public Observable<D> observable() {
        return builtObservable()
                .unsubscribeOn(subscribeOn.getScheduler())
                .subscribeOn(subscribeOn.getScheduler())
                .observeOn(observeOn.getScheduler());
    }

    /**
     * Observable built for every use case
     */
    protected abstract Observable<D> builtObservable();
}