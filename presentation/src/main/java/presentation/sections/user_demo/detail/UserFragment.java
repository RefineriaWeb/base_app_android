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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import base.app.android.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import domain.sections.user_demo.common.UserView;
import domain.sections.user_demo.detail.UserDemoPresenter;
import domain.sections.user_demo.entities.UserDemoEntity;
import presentation.foundation.BasePresenterFragment;
import presentation.sections.user_demo.UserViewGroup;
import rx.Observable;
import rx.Subscription;

public class UserFragment extends BasePresenterFragment<UserDemoPresenter> implements UserView {
    @Bind(R.id.pb_loading) protected View pb_loading;
    @Bind(R.id.user_view_group) protected UserViewGroup user_view_group;

    @Nullable
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        getApplicationComponent().inject(this);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public Subscription showUser(Observable<UserDemoEntity> oUser) {
        showProgress();

        return oUser.doOnCompleted(() -> hideProgress())
                .subscribe(user -> user_view_group.bind(user));
    }

    public void showProgress() {
        pb_loading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        pb_loading.setVisibility(View.GONE);
    }

    @OnClick(R.id.bt_go_to_search_user)
    protected void bt_go_to_search_user() {
        presenter.goToSearchScreen();
    }
}
