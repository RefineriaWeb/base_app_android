package com.refineriaweb.app.presentation.sections.demo;

import com.refineriaweb.app.R;
import com.refineriaweb.app.presentation.foundation.BaseTest;
import com.squareup.spoon.Spoon;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GetUserTestRefineriaWeb extends BaseTest {

    @Test public void _1_Get_UserDemo() {
        onView(withId(R.id.et_name)).perform(click(), replaceText("RefineriaWeb"));
        onView(withId(R.id.bt_find_user)).perform(click());
        mediumWait();

        onView(withId(R.id.tv_id)).check(matches(withText("Id:8580307")));
        Spoon.screenshot(getCurrentActivity(), "userRefineriaWeb");
    }

    @Test public void _2_Get_Cached_UserDemo() {
        mediumWait();
        onView(withId(R.id.tv_id)).check(matches(withText("Id:8580307")));
    }
}
