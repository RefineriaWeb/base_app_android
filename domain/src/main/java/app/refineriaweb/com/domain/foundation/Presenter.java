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

package app.refineriaweb.com.domain.foundation;

import app.refineriaweb.com.domain.sections.Wireframe;

public abstract class Presenter<V extends BaseView> implements Disposable {
    protected V view;
    protected final Wireframe wireframe;

    public Presenter(Wireframe wireframe) {
        this.wireframe = wireframe;
    }

    public void attachView(V view) {
        this.view = view;
    }
}