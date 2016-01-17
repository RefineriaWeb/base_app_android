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

import android.os.Bundle;

import base.app.android.R;
import domain.sections.Wireframe;
import presentation.foundation.BaseApp;
import presentation.foundation.SingleFragmentActivity;
import presentation.foundation.SingleFragmentActivity_;
import presentation.sections.dashboard.DashBoardActivity_;
import presentation.sections.user_demo.detail.UserFragment_;
import presentation.sections.user_demo.list.UsersFragment_;
import presentation.sections.user_demo.search.SearchUserFragment;
import presentation.sections.user_demo.search.SearchUserFragment_;

public class WireframeDomain implements Wireframe {
    private  final BaseApp baseApp;

    public WireframeDomain(BaseApp baseApp) {
        this.baseApp = baseApp;
    }

    @Override public void dashboard() {
        DashBoardActivity_.intent(baseApp.getLiveActivity())
                .start();
    }

    @Override public void usersScreen() {
        String title = baseApp.getString(R.string.users);
        SingleFragmentActivity_.intent(baseApp.getLiveActivity())
                .extra(SingleFragmentActivity.Behaviour.TITLE_KEY, title)
                .extra(SingleFragmentActivity.Behaviour.SHOW_BACK_KEY, false)
                .extra(SingleFragmentActivity.Behaviour.FRAGMENT_CLASS_KEY, UsersFragment_.class)
                .start();
    }

    @Override public void userScreen() {
        String title = baseApp.getString(R.string.user);
        SingleFragmentActivity_.intent(baseApp.getLiveActivity())
                .extra(SingleFragmentActivity.Behaviour.TITLE_KEY, title)
                .extra(SingleFragmentActivity.Behaviour.FRAGMENT_CLASS_KEY, UserFragment_.class)
                .start();
    }

    @Override public void searchUserScreen() {
        Bundle bundleFragment = new Bundle();
        bundleFragment.putString(SearchUserFragment.HELLO_FROM_BUNDLE_WIREFRAME_KEY, "Hi from wireframe bundle");

        String title = baseApp.getString(R.string.find_user);
        SingleFragmentActivity_.intent(baseApp.getLiveActivity())
                .extra(SingleFragmentActivity.Behaviour.TITLE_KEY, title)
                .extra(SingleFragmentActivity.Behaviour.FRAGMENT_CLASS_KEY, SearchUserFragment_.class)
                .extra(SingleFragmentActivity.Behaviour.BUNDLE_FOR_FRAGMENT, bundleFragment)
                .start();
    }

    @Override public void popCurrentScreen() {
        baseApp.getLiveActivity().finish();
    }
}
