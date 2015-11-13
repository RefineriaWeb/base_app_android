package com.refineriaweb.app.presentation.sections.user_demo.users;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.refineriaweb.app.R;
import com.refineriaweb.app.presentation.foundation.BaseFragment;
import com.refineriaweb.app.presentation.sections.user_demo.UserViewGroup;
import com.refineriaweb.app.presentation.sections.user_demo.UserViewGroup_;
import com.refineriaweb.app.presentation.utilities.recyclerview_adapter.RecyclerViewAdapterBase;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;
import app.refineriaweb.com.domain.sections.user_demo.list.UsersDemoPresenter;
import app.refineriaweb.com.domain.sections.user_demo.list.UsersView;

@EFragment(R.layout.users_fragment)
public class UsersFragment extends BaseFragment<UsersDemoPresenter> implements UsersView {
    @ViewById protected View pb_loading;
    @ViewById protected RecyclerView rv_users;
    @Bean protected UserAdapter userAdapter;

    @Override protected void init() {
        super.init();
        getApplicationComponent().inject(this);
    }

    @Override protected void initViews() {
        super.initViews();
        presenter.attachView(this);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        rv_users.setHasFixedSize(true);
        rv_users.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        userAdapter.addListener((user, userViewGroup) -> presenter.goToDetail(user));
        rv_users.setAdapter(userAdapter);
    }

    @Override public void showProgress() {
        pb_loading.setVisibility(View.VISIBLE);
        rv_users.setVisibility(View.INVISIBLE);
    }

    @Override public void hideProgress() {
        pb_loading.setVisibility(View.INVISIBLE);
        rv_users.setVisibility(View.VISIBLE);
    }

    @Override public void showData(List<UserDemoEntity> users) {
        userAdapter.setAll(users);
    }

    @Override public void showError(String message) {
        showToast(message);
    }

    @EBean static protected class UserAdapter extends RecyclerViewAdapterBase<UserDemoEntity, UserViewGroup> {
        @RootContext protected Context context;

        @Override protected UserViewGroup onCreateItemView(ViewGroup parent, int viewType) {
            return UserViewGroup_.build(context);
        }
    }
}