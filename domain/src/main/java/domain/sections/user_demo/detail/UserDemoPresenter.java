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

import domain.foundation.Presenter;
import domain.foundation.helpers.ParserException;
import domain.foundation.schedulers.ObserveOn;
import domain.foundation.schedulers.SubscribeOn;
import domain.sections.UI;
import domain.sections.Wireframe;
import domain.sections.user_demo.common.UserView;

public class UserDemoPresenter extends Presenter<UserView> {
    private final GetSelectedDemoUserListUseCase useCase;

    @Inject public UserDemoPresenter(Wireframe wireframe, SubscribeOn subscribeOn, ObserveOn observeOn, ParserException parserException, UI ui, GetSelectedDemoUserListUseCase useCase) {
        super(wireframe, subscribeOn, observeOn, parserException, ui);
        this.useCase = useCase;
    }

    @Override public void attachView(UserView view) {
        super.attachView(view);

        safetyReportError(useCase.observable())
                .dispose(observable -> view.showUser(observable));
    }

    public void goToSearchScreen() {
        wireframe.searchUserScreen();
    }
}
