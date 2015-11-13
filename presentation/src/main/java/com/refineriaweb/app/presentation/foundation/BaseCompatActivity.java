package com.refineriaweb.app.presentation.foundation;

import android.support.v7.app.AppCompatActivity;

import com.refineriaweb.app.presentation.internal.di.ApplicationComponent;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

import app.refineriaweb.com.domain.sections.Wireframe;

@EActivity
public abstract class BaseCompatActivity extends AppCompatActivity  {
    @Inject protected Wireframe wireframe;

    @AfterInject protected void init() {
        getApplicationComponent().inject(this);
    }

    @AfterViews protected void initViews() {}

    public BaseApp getBaseApp() {
        return ((BaseApp)getApplication());
    }

    public ApplicationComponent getApplicationComponent() {
        return getBaseApp().getApplicationComponent();
    }
}