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

import presentation.foundation.BaseCompatActivity;
import presentation.sections.user_demo.user.UserFragment;
import presentation.sections.user_demo.users.UsersFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton @Component(modules = {DomainPresentationModule.class, DataPresentationModule.class, ApplicationModule.class})
public interface ApplicationComponent {
    void inject(BaseCompatActivity baseCompatActivity);
}
