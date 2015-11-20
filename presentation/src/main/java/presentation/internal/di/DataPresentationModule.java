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

package presentation.internal.di;

import javax.inject.Singleton;

import base.app.android.R;
import dagger.Module;
import dagger.Provides;
import data.internal.di.DataModule;
import data.sections.Locale;
import data.storage.RepositoryAdapter;
import presentation.foundation.BaseApp;


/**
 * Dependencies required by the data layer need to be provided in this dagger module
 */
@Module(includes = {DataModule.class, ApplicationModule.class})
public class DataPresentationModule {

    /**
     * Provides the file system for the data layer
     * @see RepositoryAdapter
     */
    @Singleton @Provides RepositoryAdapter provideRepositoryAdapter(BaseApp baseApp) {
        return () -> baseApp.getFilesDir();
    }

    /**
     * Provides the locale for the data layer
     * @see Locale
     */
    @Singleton @Provides Locale provideLocale(BaseApp baseApp) {
        return new Locale() {
            @Override public String genericError() {
                return baseApp.getString(R.string.generic_error);
            }
        };
    }

}
