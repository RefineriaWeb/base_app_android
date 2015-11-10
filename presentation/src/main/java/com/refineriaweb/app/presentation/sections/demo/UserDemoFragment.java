package com.refineriaweb.app.presentation.sections.demo;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.refineriaweb.app.R;
import com.refineriaweb.app.presentation.foundation.BaseFragment;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import javax.inject.Inject;

import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoPresenter;
import app.refineriaweb.com.domain.sections.user_demo.UserView;

@EFragment(R.layout.user_demo_fragment)
public class UserDemoFragment extends BaseFragment implements UserView {
    @ViewById protected View pb_loading, bt_find_user;
    @Inject protected UserDemoPresenter userDemoPresenter;

    @Override protected void init() {
        super.init();
        getApplicationComponent().inject(this);
    }

    @Override protected void initViews() {
        super.initViews();
        userDemoPresenter.attachView(this);
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

    @ViewById protected TextView tv_id, tv_bio;
    @StringRes protected String id, bio;
    @Override public void showUser(UserDemoEntity userDemoEntity) {
        tv_id.setText(id + userDemoEntity.getId());
        tv_bio.setText(bio + userDemoEntity.getBio());
        et_name.setText(userDemoEntity.getLogin());
    }

    @ViewById protected EditText et_name;
    @Click protected void bt_find_user() {
        userDemoPresenter.getUserByUserName(et_name.getText().toString());
    }

    @Override public void onDestroy() {
        super.onDestroy();
        userDemoPresenter.dispose();
    }
}
