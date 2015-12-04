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

package domain.sections.user_demo.list;

import javax.inject.Inject;

import domain.foundation.DefaultSubscriber;
import domain.foundation.Presenter;
import domain.foundation.lce.LcePresenterSubscriber;
import domain.sections.Locale;
import domain.sections.Wireframe;
import domain.sections.user_demo.entities.UserDemoEntity;

public class UsersDemoPresenter extends Presenter<UsersView> {
    private final GetUsersDemoUseCase getUsersDemoUseCase;
    private final SaveUserDemoSelectedListUseCase saveUserDemoSelectedListUseCase;

    @Inject public UsersDemoPresenter(Wireframe wireframe, Locale locale, GetUsersDemoUseCase getUsersDemoUseCase, SaveUserDemoSelectedListUseCase saveUserDemoSelectedListUseCase) {
        super(wireframe, locale);
        this.getUsersDemoUseCase = getUsersDemoUseCase;
        this.saveUserDemoSelectedListUseCase = saveUserDemoSelectedListUseCase;
    }

    @Override public void attachView(UsersView view) {
        super.attachView(view);
        getUsersDemoUseCase.execute(new LcePresenterSubscriber(view));
    }

    public void goToDetail(UserDemoEntity user) {
        saveUserDemoSelectedListUseCase.setUserDemoEntity(user);
        saveUserDemoSelectedListUseCase.execute(new DefaultSubscriber<Boolean>() {
            @Override public void onError(Throwable e) {
                view.showError(e.getMessage());
            }

            @Override public void onNext(Boolean saved) {
                assert saved;
                wireframe.userScreen();
            }
        });
    }

    @Override public void dispose() {
        getUsersDemoUseCase.dispose();
    }
}
