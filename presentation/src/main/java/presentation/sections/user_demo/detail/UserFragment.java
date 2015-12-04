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

package presentation.sections.user_demo.detail;

import android.view.View;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import base.app.android.R;
import domain.sections.user_demo.entities.UserDemoEntity;
import domain.sections.user_demo.common.UserView;
import domain.sections.user_demo.detail.UserDemoPresenter;
import presentation.foundation.BaseFragment;
import presentation.sections.user_demo.UserViewGroup;

@EFragment(R.layout.user_fragment)
public class UserFragment extends BaseFragment<UserDemoPresenter> implements UserView {
    @ViewById protected View pb_loading;

    @Override protected void init() {
        super.init();
        getApplicationComponent().inject(this);
    }

    @Override public void showProgress() {
        pb_loading.setVisibility(View.VISIBLE);
    }

    @Override public void hideProgress() {
        pb_loading.setVisibility(View.GONE);
    }

    @Override public void showError(String message) {
        super.showToast(message);
    }

    @ViewById protected UserViewGroup user_view_group;
    @Override public void showData(UserDemoEntity user) {
        user_view_group.bind(user);
    }

    @Click protected void bt_go_to_search_user() {
        presenter.goToSearchScreen();
    }
}
