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

package presentation.sections.user_demo.list;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import base.app.android.R;
import butterknife.Bind;
import domain.sections.user_demo.entities.UserDemoEntity;
import domain.sections.user_demo.list.UsersDemoPresenter;
import domain.sections.user_demo.list.UsersView;
import presentation.foundation.BasePresenterFragment;
import presentation.foundation.LayoutResFragment;
import presentation.sections.user_demo.UserViewGroup;
import presentation.utilities.recyclerview_adapter.RecyclerViewAdapter;
import rx.Observable;
import rx.Subscription;


@LayoutResFragment(R.layout.users_fragment)
public class UsersFragment extends BasePresenterFragment<UsersDemoPresenter> implements UsersView {
    @Bind(R.id.pb_loading) protected View pb_loading;
    @Bind(R.id.rv_users) protected RecyclerView rv_users;
    private RecyclerViewAdapter<UserDemoEntity, UserViewGroup> adapter;

    @Override protected void injectDagger() {
        getApplicationComponent().inject(this);
    }

    @Override protected void initViews() {
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        rv_users.setHasFixedSize(true);
        rv_users.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        adapter = new RecyclerViewAdapter<UserDemoEntity, UserViewGroup>() {
            @Override protected UserViewGroup onCreateItemView(ViewGroup parent, int viewType) {
                return new UserViewGroup(getActivity());
            }
        };

        adapter.addListener((user, userViewGroup) -> presenter.goToDetail(user));
        adapter.swipeToRemoveItemOn(rv_users)
                .withUndoAction()
                .notify(item -> showToast(item.getLogin()));

        rv_users.setAdapter(adapter);
    }

    @Override public Subscription showUsers(Observable<List<UserDemoEntity>> oUsers) {
        showProgress();

        return oUsers
                .doOnCompleted(() -> {
                    rv_users.setVisibility(View.VISIBLE);
                    hideProgress();
                })
                .subscribe(users ->  adapter.addAll(users));
    }

    public void showProgress() {
        pb_loading.setVisibility(View.VISIBLE);
        rv_users.setVisibility(View.INVISIBLE);
    }

    public void hideProgress() {
        pb_loading.setVisibility(View.INVISIBLE);
        rv_users.setVisibility(View.VISIBLE);
    }
}
