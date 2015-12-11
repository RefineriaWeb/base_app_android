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

package domain.sections.user_demo.search;

import javax.inject.Inject;

import domain.foundation.Presenter;
import domain.foundation.lce.LcePresenterSubscriber;
import domain.sections.Locale;
import domain.sections.Wireframe;
import domain.sections.user_demo.common.UserView;

public class SearchUserDemoPresenter extends Presenter<UserView, SearchUserDemoUseCase> {

    @Inject public SearchUserDemoPresenter(SearchUserDemoUseCase useCase, Wireframe wireframe, Locale locale) {
        super(useCase, wireframe, locale);
    }

    public void getUserByUserName(String username) {
        useCase.setName(username);
        if (username == null || username.isEmpty()) view.showError(locale.errorNonEmptyFields());
        else useCase.execute(new LcePresenterSubscriber(view));
    }
}
