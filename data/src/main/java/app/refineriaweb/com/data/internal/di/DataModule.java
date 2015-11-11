package app.refineriaweb.com.data.internal.di;

import javax.inject.Singleton;

import app.refineriaweb.com.data.net.RestApi;
import app.refineriaweb.com.data.sesions.user_demo.UserDemoDataRepository;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoRepository;
import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module public class DataModule {
    @Provides RestApi provideEndpoints() {
        return new Retrofit.Builder()
                .baseUrl(RestApi.URL_BASE)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RestApi.class);
    }

    @Provides @Singleton public UserDemoRepository provideUserDemoDataRepository(UserDemoDataRepository userDemoDataRepository) {
        return userDemoDataRepository;
    }
}

