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

package domain.sections.launch;

import javax.inject.Inject;

import domain.foundation.Presenter;
import domain.sections.Locale;
import domain.sections.Wireframe;

public class LaunchPresenter extends Presenter<LaunchView, LaunchUseCase> {

    @Inject LaunchPresenter(LaunchUseCase useCase, Wireframe wireframe, Locale locale) {
        super(useCase, wireframe, locale);
    }

    @Override public void attachView(LaunchView view) {
        super.attachView(view);
        wireframe.dashboard();
    }

    @Override public void dispose() {
        useCase.dispose();
    }
}
