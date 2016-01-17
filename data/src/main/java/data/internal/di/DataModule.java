/*
 * Copyright 2015 RefineriaWeb
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package data.internal.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import data.cache.RxProviders;
import data.net.RestApi;
import data.sections.user_demo.UserDemoDataRepository;
import data.storage.RepositoryAdapter;
import domain.sections.user_demo.UserDemoRepository;
import io.rx_cache.internal.RxCache;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Dagger module for data layer. {@link #provideRestApi} define the interface used by retrofit in order
 * to generate every endpoint required
 *
 * {@link #provideUserDemoDataRepository} is an example of repository. UserDemoDataRepository implements the interface defined
 * in the domain layer, it required to be able to launch the application.
 */
@Module
public class DataModule {

    @Singleton @Provides RestApi provideRestApi() {
        return new Retrofit.Builder()
                .baseUrl(RestApi.URL_BASE)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RestApi.class);
    }

    @Singleton @Provides RxProviders provideRxProviders(RepositoryAdapter repositoryAdapter) {
        return new RxCache.Builder()
                .persistence(repositoryAdapter.cacheDirectory())
                .using(RxProviders.class);
    }

    @Provides @Singleton public UserDemoRepository provideUserDemoDataRepository(UserDemoDataRepository userDemoDataRepository) {
        return userDemoDataRepository;
    }
}

