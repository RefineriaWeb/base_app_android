package com.refineriaweb.app.presentation.internal.di;

import android.content.Context;

import com.refineriaweb.app.R;
import com.refineriaweb.app.presentation.foundation.BaseApp;
import com.refineriaweb.app.presentation.foundation.BaseToolbarActivity;
import com.refineriaweb.app.presentation.sections.user_demo.user.HostUserActivity_;
import com.refineriaweb.app.presentation.sections.user_demo.users.HostUsersActivity_;

import javax.inject.Singleton;

import app.refineriaweb.com.data.internal.di.DataModule;
import app.refineriaweb.com.domain.foundation.schedulers.ObserveOn;
import app.refineriaweb.com.domain.foundation.schedulers.SubscribeOn;
import app.refineriaweb.com.domain.sections.Wireframe;
import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module(includes = DataModule.class) public class ApplicationModule {
    private final BaseApp baseApp;

    public ApplicationModule(BaseApp baseApp) {
        this.baseApp = baseApp;
    }

    @Provides @Singleton Context provideApplicationContext() {
        return baseApp;
    }

    @Singleton @Provides SubscribeOn provideSubscribeOn() {
        return (() -> Schedulers.newThread());
    }

    @Singleton @Provides ObserveOn provideObserveOn() {
        return (() -> AndroidSchedulers.mainThread());
    }

    @Singleton @Provides Wireframe provideAndroidWireframe() {
        return new Wireframe() {
            @Override public void userScreen() {
                String title = baseApp.getString(R.string.user);
                HostUserActivity_.intent(baseApp.getCurrentActivity())
                        .extra(BaseToolbarActivity.Behaviour.TITLE_KEY, title)
                        .start();
            }

            @Override public void usersScreen() {
                String title = baseApp.getString(R.string.users);
                HostUsersActivity_.intent(baseApp.getCurrentActivity())
                        .extra(BaseToolbarActivity.Behaviour.TITLE_KEY, title)
                        .extra(BaseToolbarActivity.Behaviour.SHOW_BACK_KEY, false)
                        .start();
            }
        };
    }
}
