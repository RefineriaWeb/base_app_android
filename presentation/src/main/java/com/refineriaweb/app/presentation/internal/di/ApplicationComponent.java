package com.refineriaweb.app.presentation.internal.di;

import com.refineriaweb.app.presentation.activities.BaseActivity;
import com.refineriaweb.app.presentation.activities.demo.HostUserDemoActivity;
import com.refineriaweb.app.presentation.fragments.BaseFragment;
import com.refineriaweb.app.presentation.fragments.demo.UserDemoFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton @Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);
        void inject(HostUserDemoActivity launchActivity);

    void inject(BaseFragment baseFragment);
        void inject(UserDemoFragment fragmentLogin);
}
