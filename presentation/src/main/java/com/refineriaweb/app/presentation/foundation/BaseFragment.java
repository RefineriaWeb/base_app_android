package com.refineriaweb.app.presentation.foundation;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.refineriaweb.app.presentation.foundation.BaseActivity;
import com.refineriaweb.app.presentation.internal.di.ApplicationComponent;
import com.refineriaweb.app.presentation.navigation.Navigator;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import javax.inject.Inject;

@EFragment
public abstract class BaseFragment extends Fragment {
    @Inject Navigator navigator;

    @AfterInject protected void init() {
        getApplicationComponent().inject(this);
    }

    @AfterViews protected void initViews() {}

    protected ApplicationComponent getApplicationComponent() {
        return ((BaseActivity)getActivity()).getApplicationComponent();
    }

    protected void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
