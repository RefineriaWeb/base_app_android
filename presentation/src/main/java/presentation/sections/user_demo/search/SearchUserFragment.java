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

import android.os.Bundle;
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
import rx.Observable;
import rx.Subscription;

@EFragment(R.layout.user_search_fragment)
public class SearchUserFragment extends BasePresenterFragment<SearchUserDemoPresenter> implements UserView {
    public static final String HELLO_FROM_BUNDLE_WIREFRAME_KEY = "hello_from_bundle_key";

    @ViewById protected UserViewGroup user_view_group;

    @Override protected void init() {
        super.init();
        getApplicationComponent().inject(this);
    }

    @Override protected void initViews() {
        Bundle bundle = getArguments();
        String helloFromBundle = bundle != null ? bundle.getString(HELLO_FROM_BUNDLE_WIREFRAME_KEY, "") : "";
        if (!helloFromBundle.isEmpty())showSnackBar(helloFromBundle);

        super.initViews();
    }

    @Override public Subscription showUser(Observable<UserDemoEntity> oUser) {
        showLoading();

        return oUser.subscribe(user -> {
            user_view_group.bind(user);
            hideLoading();
        });
    }

    @Override protected void showLoading() {
        super.showLoading();
        user_view_group.setVisibility(View.GONE);
    }

    @Override protected void hideLoading() {
        super.hideLoading();
        user_view_group.setVisibility(View.VISIBLE);
    }

    @ViewById protected EditText et_name;
    @Click protected void bt_find_user() {
        presenter.getUserByUserName(et_name.getText().toString());
    }
}
