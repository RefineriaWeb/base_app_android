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

import domain.sections.UI;
import domain.sections.Wireframe;
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
    protected final UI ui;
    private final CompositeSubscription subscriptions;

    public Presenter(Wireframe wireframe, UI ui) {
        this.wireframe = wireframe;
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

    protected void subscriptions(Subscription subscription) {
        this.subscriptions.add(subscription);
    }

    private void unsubscribe() {
        if (!subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }

    public void dispose() {
        unsubscribe();
    }
}
