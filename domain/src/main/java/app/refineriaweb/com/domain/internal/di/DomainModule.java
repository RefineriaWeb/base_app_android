package app.refineriaweb.com.domain.internal.di;

import javax.inject.Singleton;

import app.refineriaweb.com.domain.foundation.schedulers.SubscribeOn;
import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

@Module
public class DomainModule {

    @Singleton @Provides SubscribeOn provideSubscribeOn() {
        return (() -> Schedulers.newThread());
    }

}
