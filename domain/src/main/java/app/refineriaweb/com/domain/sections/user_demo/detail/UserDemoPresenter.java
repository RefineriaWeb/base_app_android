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

package app.refineriaweb.com.domain.sections.user_demo.detail;

import javax.inject.Inject;

import app.refineriaweb.com.domain.foundation.Presenter;
import app.refineriaweb.com.domain.foundation.lce.LcePresenterSubscriber;
import app.refineriaweb.com.domain.sections.Wireframe;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;

public class UserDemoPresenter extends Presenter<UserView> {
    private final GetUserDemoUseCase getUserDemo;
    private final GetSelectedDemoUserListUseCase getSelectedDemoUserListUseCase;

    @Inject public UserDemoPresenter(Wireframe wireframe, GetUserDemoUseCase getUserDemo,
                                     GetSelectedDemoUserListUseCase getSelectedDemoUserListUseCase) {
        super(wireframe);
        this.getUserDemo = getUserDemo;
        this.getSelectedDemoUserListUseCase = getSelectedDemoUserListUseCase;
    }

    @Override public void attachView(UserView view) {
        super.attachView(view);
        getSelectedDemoUserListUseCase.getCachedUser(new LcePresenterSubscriber<UserDemoEntity, UserView>(view) {});
    }

    public void getUserByUserName(String username) {
        getUserDemo.getUser(username, new LcePresenterSubscriber<UserDemoEntity, UserView>(view) {
        });
    }

    @Override public void dispose() {
        getUserDemo.dispose();
        getSelectedDemoUserListUseCase.dispose();
    }
}
