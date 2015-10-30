package com.refineriaweb.app.presentation.activities;


import android.support.v7.app.AppCompatActivity;

import com.refineriaweb.app.presentation.internal.di.ApplicationComponent;
import com.refineriaweb.app.presentation.navigation.Navigator;
import com.refineriaweb.app.presentation.utilities.AndroidApplication;

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
        return ((AndroidApplication)getApplication()).getApplicationComponent();
    }
}
