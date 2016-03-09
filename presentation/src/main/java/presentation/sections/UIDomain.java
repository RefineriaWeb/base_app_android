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

package presentation.sections;

import base.app.android.R;
import domain.sections.UI;
import presentation.foundation.BaseApp;
import rx.Observable;
import rx.Subscription;

public class UIDomain implements UI {
    private final BaseApp baseApp;

    public UIDomain(BaseApp baseApp) {
        this.baseApp = baseApp;
    }

    @Override public Observable<String> errorNonEmptyFields() {
        return Observable.just(baseApp.getString(R.string.fill_missing_fields));
    }

    @Override public Subscription showFeedback(Observable<String> oFeedback) {
        return oFeedback.subscribe(feedback -> {
            baseApp.getLiveActivity()
                    .getCurrentPresenterFragment().showToast(feedback);
        });
    }

    @Override public Subscription showAnchoredScreenFeedback(Observable<String> oFeedback) {
        return oFeedback.subscribe(feedback -> {
            baseApp.getLiveActivity()
                    .getCurrentPresenterFragment().showSnackBar(feedback);
        });
    }

    @Override public void showLoading() {
        baseApp.getLiveActivity().showLoading();
    }

    @Override public void hideLoading() {
        baseApp.getLiveActivity().hideLoading();
    }

}
