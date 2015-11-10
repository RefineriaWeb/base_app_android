package com.refineriaweb.app.presentation.foundation;


import android.support.v7.app.AppCompatActivity;

import com.refineriaweb.app.presentation.internal.di.ApplicationComponent;
import com.refineriaweb.app.presentation.navigation.Navigator;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@EActivity
public abstract class BaseActivity extends AppCompatActivity {
    @Inject Navigator navigator;

    @AfterInject protected void init() {
        getApplicationComponent().inject(this);
    }

    @AfterViews protected void initViews() {}

    public ApplicationComponent getApplicationComponent() {
        return ((BaseApp)getApplication()).getApplicationComponent();
    }
}
