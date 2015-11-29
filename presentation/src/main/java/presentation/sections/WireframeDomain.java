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
import domain.sections.Wireframe;
import presentation.foundation.BaseApp;
import presentation.foundation.BaseToolbarActivity;
import presentation.sections.user_demo.search_user.HostSearchUserActivity_;
import presentation.sections.user_demo.user.HostUserActivity_;
import presentation.sections.user_demo.users.HostUsersActivity_;

public class WireframeDomain implements Wireframe {
    private  final BaseApp baseApp;

    public WireframeDomain(BaseApp baseApp) {
        this.baseApp = baseApp;
    }

    @Override public void usersScreen() {
        String title = baseApp.getString(R.string.users);
        HostUsersActivity_.intent(baseApp.getLiveActivity())
                .extra(BaseToolbarActivity.Behaviour.TITLE_KEY, title)
                .extra(BaseToolbarActivity.Behaviour.SHOW_BACK_KEY, false)
                .start();
    }

    @Override public void userScreen() {
        String title = baseApp.getString(R.string.user);
        HostUserActivity_.intent(baseApp.getLiveActivity())
                .extra(BaseToolbarActivity.Behaviour.TITLE_KEY, title)
                .start();
    }

    @Override public void searchUserScreen() {
        String title = baseApp.getString(R.string.user);
        HostSearchUserActivity_.intent(baseApp.getLiveActivity())
                .extra(BaseToolbarActivity.Behaviour.TITLE_KEY, title)
                .start();
    }
}
