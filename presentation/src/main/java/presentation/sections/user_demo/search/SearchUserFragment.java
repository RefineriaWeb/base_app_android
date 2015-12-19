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

package presentation.sections.user_demo.search;

import android.view.View;
import android.widget.EditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import base.app.android.R;
import domain.sections.user_demo.common.UserView;
import domain.sections.user_demo.entities.UserDemoEntity;
import domain.sections.user_demo.search.SearchUserDemoPresenter;
import presentation.foundation.BasePresenterFragment;
import presentation.sections.user_demo.UserViewGroup;

@EFragment(R.layout.user_search_fragment)
public class SearchUserFragment extends BasePresenterFragment<SearchUserDemoPresenter> implements UserView {
    @ViewById protected UserViewGroup user_view_group;

    @Override protected void init() {
        super.init();
        getApplicationComponent().inject(this);
    }

    @Override public void showProgress() {
        showLoading();
    }

    @Override public void hideProgress() {
        hideLoading();
    }

    @Override public void showError(String message) {
        super.showSnackBar(message);
    }

    @Override public void showResult(UserDemoEntity user) {
        user_view_group.setVisibility(View.VISIBLE);
        user_view_group.bind(user);
    }

    @ViewById protected EditText et_name;
    @Click protected void bt_find_user() {
        presenter.getUserByUserName(et_name.getText().toString());
    }
}
