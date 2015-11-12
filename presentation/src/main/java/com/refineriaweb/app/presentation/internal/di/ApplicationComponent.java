package com.refineriaweb.app.presentation.internal.di;

import com.refineriaweb.app.presentation.foundation.BaseCompatActivity;
import com.refineriaweb.app.presentation.sections.user_demo.user.UserFragment;
import com.refineriaweb.app.presentation.sections.user_demo.users.UsersFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton @Component(modules = {DomainPresentationModule.class, DataPresentationModule.class, ApplicationModule.class})
public interface ApplicationComponent {
    void inject(BaseCompatActivity baseCompatActivity);

    void inject(UserFragment userFragment);
    void inject(UsersFragment usersFragment);
}
