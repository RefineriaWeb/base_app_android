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

package domain.sections.dashboard;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import domain.foundation.UseCase;
import domain.sections.UI;
import rx.Observable;

public class GetMenuItemsUseCase extends UseCase<List<ItemMenu>> {
    public static final int ID_USERS = 1, ID_USER = 2, ID_SEARCH_USER = 3;
    private final DashboardItemsMenu dashboardItemsMenu;

    @Inject GetMenuItemsUseCase(DashboardItemsMenu dashboardItemsMenu, UI ui) {
        super(ui);
        this.dashboardItemsMenu = dashboardItemsMenu;
    }

    @Override public Observable<List<ItemMenu>> observable() {
        ItemMenu users = new ItemMenu(ID_USERS);
        dashboardItemsMenu.configureUsers(users);

        ItemMenu user = new ItemMenu(ID_USER);
        dashboardItemsMenu.configureUser(user);

        ItemMenu searchUser = new ItemMenu(ID_SEARCH_USER);
        dashboardItemsMenu.configureSearchUser(searchUser);

        return Observable.just(Arrays.asList(users, user, searchUser));
    }
}
