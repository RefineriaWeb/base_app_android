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

package presentation.di;


import com.refineriaweb.app.R;
import presentation.foundation.BaseApp;
import presentation.foundation.BaseToolbarActivity;
import presentation.sections.user_demo.user.HostUserActivity_;
import presentation.sections.user_demo.users.HostUsersActivity_;

import javax.inject.Singleton;

import domain.foundation.schedulers.ObserveOn;
import app.refineriaweb.com.domain.internal.di.DomainModule;
import app.refineriaweb.com.domain.sections.Wireframe;
import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;

@Module(includes = {DomainModule.class, ApplicationModule.class})
public class DomainPresentationModule {

    @Singleton @Provides ObserveOn provideObserveOn() {
        return (() -> AndroidSchedulers.mainThread());
    }

    @Singleton @Provides Wireframe provideAndroidWireframe(BaseApp baseApp) {
        return new Wireframe() {

        };
    }
}
