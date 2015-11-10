package com.refineriaweb.app.presentation.foundation;

import android.app.Application;

import com.refineriaweb.app.presentation.internal.di.ApplicationComponent;
import com.refineriaweb.app.presentation.internal.di.ApplicationModule;
import com.refineriaweb.app.presentation.internal.di.DaggerApplicationComponent;

public class BaseApp extends Application {
    private ApplicationComponent applicationComponent;
    private BaseCompatActivity currentActivity = null;

    @Override public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public BaseCompatActivity getCurrentActivity(){
        return currentActivity;
    }
    public void setCurrentActivity(BaseCompatActivity currentActivity){
        this.currentActivity = currentActivity;
    }
}
