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

import dagger.Module;
import dagger.Provides;
import domain.di.DomainModule;
import domain.foundation.schedulers.ObserveOn;
import domain.sections.Locale;
import domain.sections.Wireframe;
import domain.sections.dashboard.DashboardItemsMenu;
import presentation.foundation.BaseApp;
import presentation.sections.LocaleDomain;
import presentation.sections.WireframeDomain;
import presentation.sections.dashboard.DashboardItemsMenuDomain;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Dependencies required by the domain layer need to be provided in this dagger module
 */
@Module(includes = {DomainModule.class, ApplicationModule.class})
public class DomainPresentationModule {

    /**
     * Provides the Scheduler which will be use for any Observable at the domain layer
     * to observeOn
     */
    @Singleton @Provides ObserveOn provideObserveOn() {
        return (() -> AndroidSchedulers.mainThread());
    }

    /**
     * Provides the locale for the domain layer
     * @see Locale
     */
    @Singleton @Provides Locale provideLocale(BaseApp baseApp) {
        return new LocaleDomain(baseApp);
    }

    /**
     * Implements the navigation system required by the domain layer
     * using intents in order to load the following screens
     */
    @Singleton @Provides Wireframe provideAndroidWireframe(BaseApp baseApp) {
        return new WireframeDomain(baseApp);
    }

    /**
     * Implements the DashboardItemsMenu
     */
    @Singleton @Provides DashboardItemsMenu provideDashboardItemsMenuDomain(BaseApp baseApp) {
        return new DashboardItemsMenuDomain(baseApp);
    }
}
