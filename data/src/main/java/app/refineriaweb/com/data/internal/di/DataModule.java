package app.refineriaweb.com.data.internal.di;

import javax.inject.Singleton;

import app.refineriaweb.com.data.net.ConfigEndpoints;
import app.refineriaweb.com.data.net.Endpoints;
import app.refineriaweb.com.data.repositories.UserDemoDataRepository;
import app.refineriaweb.com.domain.repositories.demo.UserDemoRepository;
import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module public class DataModule {
    @Provides Endpoints provideEndpoints() {
        return new Retrofit.Builder()
                .baseUrl(ConfigEndpoints.URL_BASE)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Endpoints.class);
    }


    @Provides @Singleton public UserDemoRepository provideUserDemoDataRepository(UserDemoDataRepository userDemoDataRepository) {
        return userDemoDataRepository;
    }
}

