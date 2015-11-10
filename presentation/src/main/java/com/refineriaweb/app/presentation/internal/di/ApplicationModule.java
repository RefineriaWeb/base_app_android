package com.refineriaweb.app.presentation.internal.di;

import android.content.Context;

import com.refineriaweb.app.presentation.foundation.BaseApp;
import com.refineriaweb.app.presentation.navigation.Navigator;

import javax.inject.Singleton;

import app.refineriaweb.com.data.internal.di.DataModule;
import app.refineriaweb.com.domain.foundation.schedulers.ObserveOn;
import app.refineriaweb.com.domain.foundation.schedulers.SubscribeOn;
import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module(includes = DataModule.class) public class ApplicationModule {
    private final BaseApp application;

    public ApplicationModule(BaseApp application) {
        this.application = application;
    }

    @Provides @Singleton Context provideApplicationContext() {
        return application;
    }

    @Singleton @Provides Navigator provideNavigator() {
        return new Navigator();
    }

    @Singleton @Provides SubscribeOn provideSubscribeOn() {
        return (() -> Schedulers.newThread());
    }

    @Singleton @Provides ObserveOn provideObserveOn() {
        return (() -> AndroidSchedulers.mainThread());
    }
}
