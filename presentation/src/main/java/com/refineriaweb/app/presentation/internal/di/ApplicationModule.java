package com.refineriaweb.app.presentation.internal.di;

import android.content.Context;

import com.refineriaweb.app.presentation.navigation.Navigator;
import com.refineriaweb.app.presentation.utilities.AndroidApplication;

import javax.inject.Singleton;

import app.refineriaweb.com.data.internal.di.DataModule;
import app.refineriaweb.com.domain.schedulers.ObserveOn;
import app.refineriaweb.com.domain.schedulers.SubscribeOn;
import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module(includes = DataModule.class) public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
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
