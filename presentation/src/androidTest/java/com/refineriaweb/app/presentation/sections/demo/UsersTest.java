package com.refineriaweb.app.presentation.sections.demo;

import android.support.test.espresso.contrib.RecyclerViewActions;

import com.refineriaweb.app.R;
import com.refineriaweb.app.presentation.common.BaseTest;
import com.squareup.spoon.Spoon;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersTest extends BaseTest {
    private static final int ID_USER = 21, INDEX_LIST = 11, ID_USER_REFINERIA = 8580307;
    private static final String USERNAME = "technoweenie";
    private static final String USERNAME_REFINERIA = "RefineriaWeb";

    @Test public void _1_Get_Users() {
        mediumWait();
        onView(withId(R.id.rv_users)).perform(RecyclerViewActions.scrollToPosition(INDEX_LIST));
        Spoon.screenshot(getCurrentActivity(), "users");
    }

    @Test public void _2_Get_User() {
        mediumWait();
        onView(withId(R.id.rv_users)).perform(RecyclerViewActions.scrollToPosition(INDEX_LIST));
        onView(withId(R.id.rv_users)).perform(RecyclerViewActions.actionOnItemAtPosition(INDEX_LIST, click()));
        onView(withId(R.id.tv_name)).check(matches(withText(ID_USER+":"+USERNAME)));

        Spoon.screenshot(getCurrentActivity(), USERNAME);
    }

    @Test public void _3_Search_User() {
        mediumWait();
        onView(withId(R.id.rv_users)).perform(RecyclerViewActions.scrollToPosition(INDEX_LIST));
        onView(withId(R.id.rv_users)).perform(RecyclerViewActions.actionOnItemAtPosition(INDEX_LIST, click()));

        onView(withId(R.id.et_name)).perform(click(), replaceText(USERNAME_REFINERIA), closeSoftKeyboard());
        onView(withId(R.id.bt_find_user)).perform(click());
        mediumWait();

        onView(withId(R.id.tv_name)).check(matches(withText(ID_USER_REFINERIA+":"+USERNAME_REFINERIA)));
        Spoon.screenshot(getCurrentActivity(), USERNAME_REFINERIA);
    }

}
