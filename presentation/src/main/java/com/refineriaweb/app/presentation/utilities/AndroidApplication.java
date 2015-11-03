package com.refineriaweb.app.presentation.utilities;

import android.app.Application;

import com.refineriaweb.app.presentation.activities.BaseAppCompatActivity;
import com.refineriaweb.app.presentation.internal.di.ApplicationComponent;
import com.refineriaweb.app.presentation.internal.di.ApplicationModule;
import com.refineriaweb.app.presentation.internal.di.DaggerApplicationComponent;

import org.androidannotations.annotations.EApplication;

@EApplication
public class AndroidApplication extends Application {
    private ApplicationComponent applicationComponent;
    private BaseAppCompatActivity mCurrentActivity = null;

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

    public BaseAppCompatActivity getCurrentActivity(){
        return mCurrentActivity;
    }
    public void setCurrentActivity(BaseAppCompatActivity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }
}
