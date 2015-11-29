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

package presentation.sections.dashboard;

import base.app.android.R;
import domain.sections.dashboard.DashboardItemsMenu;
import domain.sections.dashboard.ItemMenu;
import presentation.foundation.BaseApp;

public class DashboardItemsMenuDomain implements DashboardItemsMenu {
    private final BaseApp baseApp;

    public DashboardItemsMenuDomain(BaseApp baseApp) {
        this.baseApp = baseApp;
    }

    @Override public void configureUsers(ItemMenu itemMenu) {
        itemMenu.setTitle(baseApp.getString(R.string.users));
        itemMenu.setImageResource(R.drawable.ic_users);
    }

    @Override public void configureUser(ItemMenu itemMenu) {
        itemMenu.setTitle(baseApp.getString(R.string.user));
        itemMenu.setImageResource(R.drawable.ic_user);
    }

    @Override public void configureSearchUser(ItemMenu itemMenu) {
        itemMenu.setTitle(baseApp.getString(R.string.find_user));
        itemMenu.setImageResource(R.drawable.ic_search);
    }
}
