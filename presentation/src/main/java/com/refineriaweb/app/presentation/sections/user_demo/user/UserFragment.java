package com.refineriaweb.app.presentation.sections.user_demo.user;

import android.view.View;
import android.widget.EditText;

import com.refineriaweb.app.R;
import com.refineriaweb.app.presentation.foundation.BaseFragment;
import com.refineriaweb.app.presentation.sections.user_demo.UserViewGroup;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;
import app.refineriaweb.com.domain.sections.user_demo.detail.UserDemoPresenter;
import app.refineriaweb.com.domain.sections.user_demo.detail.UserView;

@EFragment(R.layout.user_fragment)
public class UserFragment extends BaseFragment<UserDemoPresenter> implements UserView {
    @ViewById protected View pb_loading, bt_find_user;

    @Override protected void init() {
        super.init();
        getApplicationComponent().inject(this);
    }

    @Override public void showProgress() {
        pb_loading.setVisibility(View.VISIBLE);
        bt_find_user.setVisibility(View.GONE);
    }

    @Override public void hideProgress() {
        pb_loading.setVisibility(View.GONE);
        bt_find_user.setVisibility(View.VISIBLE);
    }

    @Override public void showError(String message) {
        super.showToast(message);
    }

    @ViewById protected UserViewGroup user_view_group;
    @Override public void showData(UserDemoEntity user) {
        user_view_group.bind(user);
    }

    @ViewById protected EditText et_name;
    @Click protected void bt_find_user() {
        presenter.getUserByUserName(et_name.getText().toString());
    }
}
