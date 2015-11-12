package com.refineriaweb.app.presentation.internal.di;

import com.refineriaweb.app.presentation.foundation.BaseApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import data.internal.di.DataModule;

@Module(includes = DataModule.class) public class ApplicationModule {
    private final BaseApp baseApp;

    public ApplicationModule(BaseApp baseApp) {
        this.baseApp = baseApp;
    }

    @Provides @Singleton BaseApp provideApplication() {
        return baseApp;
    }

}
