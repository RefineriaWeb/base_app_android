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
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import base.app.android.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import domain.sections.user_demo.common.UserView;
import domain.sections.user_demo.entities.UserDemoEntity;
import domain.sections.user_demo.search.SearchUserDemoPresenter;
import presentation.foundation.BaseFragmentActivity;
import presentation.foundation.BasePresenterFragment;
import presentation.sections.user_demo.UserViewGroup;
import rx.Observable;
import rx.Subscription;

public class SearchUserFragment extends BasePresenterFragment<SearchUserDemoPresenter> implements UserView, BaseFragmentActivity.BackButtonListener {
    public static final String HELLO_FROM_BUNDLE_WIREFRAME_KEY = "hello_from_bundle_key";
    @Bind(R.id.user_view_group) protected UserViewGroup user_view_group;
    @Bind(R.id.et_name) protected EditText et_name;


    @Nullable
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_search_fragment, container, false);
        getApplicationComponent().inject(this);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        Bundle bundle = getArguments();
        String helloFromBundle = bundle != null ? bundle.getString(HELLO_FROM_BUNDLE_WIREFRAME_KEY, "") : "";
        if (!helloFromBundle.isEmpty())showSnackBar(helloFromBundle);
    }

    @Override public Subscription showUser(Observable<UserDemoEntity> oUser) {
        showLoading();

        return oUser.doOnCompleted(() -> hideLoading())
                .subscribe(user -> user_view_group.bind(user));
    }

    @Override protected void showLoading() {
        super.showLoading();
        user_view_group.setVisibility(View.GONE);
    }

    @Override protected void hideLoading() {
        super.hideLoading();
        user_view_group.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.bt_find_user)
    protected void bt_find_user() {
        presenter.getUserByUserName(et_name.getText().toString());
    }

    @Override public boolean onBackPressed() {
        showToast("Closed on back press from fragment");
        getActivity().finish();
        return false;
    }
}
