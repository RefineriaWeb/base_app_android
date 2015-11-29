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

package presentation.sections;

import base.app.android.R;
import domain.sections.Locale;
import presentation.foundation.BaseApp;

public class LocaleDomain implements Locale {
    private final BaseApp baseApp;

    public LocaleDomain(BaseApp baseApp) {
        this.baseApp = baseApp;
    }

    @Override public String errorNonEmptyFields() {
        return baseApp.getString(R.string.fill_missing_fields);
    }
}
