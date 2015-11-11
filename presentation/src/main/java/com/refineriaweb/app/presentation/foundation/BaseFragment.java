package com.refineriaweb.app.presentation.foundation;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.refineriaweb.app.presentation.internal.di.ApplicationComponent;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import javax.inject.Inject;

import app.refineriaweb.com.domain.foundation.BaseView;
import app.refineriaweb.com.domain.foundation.Presenter;

@EFragment
public abstract class BaseFragment<P extends Presenter> extends Fragment implements BaseView {
    @Inject protected P presenter;

    @AfterInject protected void init() {}
    
    @AfterViews protected void initViews() {
        presenter.attachView(this);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((BaseCompatActivity)getActivity()).getApplicationComponent();
    }

    protected void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}
