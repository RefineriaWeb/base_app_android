package com.refineriaweb.app.presentation.internal.di;

import com.refineriaweb.app.presentation.foundation.BaseApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import data.internal.di.DataModule;
import data.storage.RepositoryAdapter;

@Module(includes = {DataModule.class, ApplicationModule.class})
public class DataPresentationModule {

    @Singleton @Provides RepositoryAdapter provideRepositoryAdapter(BaseApp baseApp) {
        return () -> baseApp.getFilesDir();
    }

}
