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

package domain.foundation;

import domain.sections.UI;
import rx.Observable;

/**
 * Base class for any UseCase.
 * The UseCase retrieves its data from an specific repository and
 * it is in charge of processing data for any presenter.
 * @see  Repository
 */

public abstract class UseCase<D> {
    protected final UI ui;

    public UseCase(UI ui) {
        this.ui = ui;
    }

    /**
     * Observable built for every use case
     */
    public abstract Observable<D> react();
}