package com.refineriaweb.app.presentation.internal.di;

import com.refineriaweb.app.presentation.foundation.BaseCompatActivity;
import com.refineriaweb.app.presentation.foundation.BaseFragment;
import com.refineriaweb.app.presentation.sections.demo.HostUserDemoActivity;
import com.refineriaweb.app.presentation.sections.demo.UserDemoFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton @Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseCompatActivity baseCompatActivity);
        void inject(HostUserDemoActivity hostUserDemoActivity);

    void inject(BaseFragment baseFragment);
        void inject(UserDemoFragment userDemoFragment);
}
