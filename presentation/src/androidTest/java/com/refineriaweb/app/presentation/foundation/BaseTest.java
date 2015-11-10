package com.refineriaweb.app.presentation.foundation;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.refineriaweb.app.presentation.sections.demo.HostUserDemoActivity_;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public abstract class BaseTest {
    public static final long SHORT_WAIT = 1000;
    public static final long MEDIUM_WAIT = 2500;
    public static final long LONG_WAIT = 5000;

    public static void shortWait() {
        waitTime(SHORT_WAIT);
    }

    public static void mediumWait() {
        waitTime(MEDIUM_WAIT);
    }

    public static void longWait() {
        waitTime(LONG_WAIT);
    }

    private static void waitTime(long time) {
        try {Thread.sleep(time);
        } catch (InterruptedException e) { e.printStackTrace();}
    }

    @Rule public ActivityTestRule<HostUserDemoActivity_> mActivityRule = new ActivityTestRule<>(HostUserDemoActivity_.class);

    @Before public void init() {}
}