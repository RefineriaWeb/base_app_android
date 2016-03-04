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

import android.content.Intent;
import android.os.Bundle;

import base.app.android.R;
import domain.sections.Wireframe;
import presentation.foundation.BaseApp;
import presentation.foundation.BaseFragmentActivity;
import presentation.foundation.SingleFragmentActivity;
import presentation.sections.dashboard.DashBoardActivity;
import presentation.sections.user_demo.detail.UserFragment;
import presentation.sections.user_demo.list.UsersFragment;
import presentation.sections.user_demo.search.SearchUserFragment;

public class WireframeDomain implements Wireframe {
    private  final BaseApp baseApp;

    public WireframeDomain(BaseApp baseApp) {
        this.baseApp = baseApp;
    }

    @Override public void dashboard() {
        baseApp.getLiveActivity().startActivity(new Intent(baseApp, DashBoardActivity.class));
    }

    @Override public void usersScreen() {
        Bundle bundle = new Bundle();
        bundle.putString(BaseFragmentActivity.Behaviour.TITLE_KEY, baseApp.getString(R.string.users));
        bundle.putBoolean(BaseFragmentActivity.Behaviour.SHOW_BACK_KEY, false);
        bundle.putSerializable(BaseFragmentActivity.Behaviour.FRAGMENT_CLASS_KEY, UsersFragment.class);
        Intent intent = new Intent(baseApp, SingleFragmentActivity.class);
        intent.putExtras(bundle);
        baseApp.getLiveActivity().startActivity(intent);
    }

    @Override public void userScreen() {
        Bundle bundle = new Bundle();
        bundle.putString(BaseFragmentActivity.Behaviour.TITLE_KEY, baseApp.getString(R.string.user));
        bundle.putSerializable(BaseFragmentActivity.Behaviour.FRAGMENT_CLASS_KEY, UserFragment.class);
        Intent intent = new Intent(baseApp, SingleFragmentActivity.class);
        intent.putExtras(bundle);
        baseApp.getLiveActivity().startActivity(intent);
    }

    @Override public void searchUserScreen() {
        Bundle bundleFragment = new Bundle();
        bundleFragment.putString(SearchUserFragment.HELLO_FROM_BUNDLE_WIREFRAME_KEY, "Hi from wireframe bundle");
        Bundle bundle = new Bundle();
        bundle.putString(BaseFragmentActivity.Behaviour.TITLE_KEY, baseApp.getString(R.string.find_user));
        bundle.putSerializable(BaseFragmentActivity.Behaviour.FRAGMENT_CLASS_KEY, SearchUserFragment.class);
        bundle.putBundle(BaseFragmentActivity.Behaviour.BUNDLE_FOR_FRAGMENT, bundleFragment);
        Intent intent = new Intent(baseApp, SingleFragmentActivity.class);
        intent.putExtras(bundle);
        baseApp.getLiveActivity().startActivity(intent);
    }

    @Override public void popCurrentScreen() {
        baseApp.getLiveActivity().finish();
    }
}
