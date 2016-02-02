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

package presentation.common;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.spoon.Spoon;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import presentation.foundation.BaseApp;
import presentation.sections.launch.LaunchActivity_;

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

    @Rule public ActivityTestRule<LaunchActivity_> mActivityRule = new ActivityTestRule<>(LaunchActivity_.class);

    @Before public void init() {}

    protected Activity getCurrentActivity() {
        BaseApp app = (BaseApp) InstrumentationRegistry.getTargetContext().getApplicationContext();
        return app.getLiveActivity();
    }

    protected void takeScreenShot(String name) {
        try {
            Spoon.screenshot(getCurrentActivity(), name);
        } catch (Exception ignore){}
    }
}