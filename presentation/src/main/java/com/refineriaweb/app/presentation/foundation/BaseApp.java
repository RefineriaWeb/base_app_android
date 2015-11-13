package com.refineriaweb.app.presentation.foundation;

import android.app.Activity;
import android.app.Application;

import com.refineriaweb.app.presentation.internal.di.ApplicationComponent;
import com.refineriaweb.app.presentation.internal.di.ApplicationModule;
import com.refineriaweb.app.presentation.internal.di.DaggerApplicationComponent;

public class BaseApp extends Application {
    private ApplicationComponent applicationComponent;

    @Override public void onCreate() {
        super.onCreate();
        initInject();
        AppCare.YesSir.takeCareOn(this);
    }

    private void initInject() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public Activity getLiveActivity(){
        return AppCare.YesSir.getLiveActivityOrNull();
    }
}
