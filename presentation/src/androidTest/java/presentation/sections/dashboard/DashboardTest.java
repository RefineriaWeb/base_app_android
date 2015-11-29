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

import android.support.test.espresso.contrib.RecyclerViewActions;

import com.squareup.spoon.Spoon;

import org.junit.Test;

import base.app.android.R;
import presentation.common.BaseTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static presentation.common.ViewActions.actionCloseDrawer;
import static presentation.common.ViewActions.actionOpenDrawer;

public class DashboardTest extends BaseTest {

    @Test public void Open_And_Close_Users() {
        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        onView(withId(R.id.rv_menu_items)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Spoon.screenshot(getCurrentActivity(), "Users");
        onView(withId(R.id.drawer_layout)).perform(actionCloseDrawer());
    }

    @Test public void Open_And_Close_User() {
        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        onView(withId(R.id.rv_menu_items)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        Spoon.screenshot(getCurrentActivity(), "User");
        onView(withId(R.id.drawer_layout)).perform(actionCloseDrawer());
    }

    @Test public void Open_And_Close_Search_User() {
        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        onView(withId(R.id.rv_menu_items)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        Spoon.screenshot(getCurrentActivity(), "SearchUser");
        onView(withId(R.id.drawer_layout)).perform(actionCloseDrawer());
    }

}
