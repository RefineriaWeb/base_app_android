package com.refineriaweb.app.presentation.foundation;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public enum AppCare {
    YesSir;

    private Activity liveActivityOrNull;

    public void takeCareOn(Application application) {
        registerActivityLifeCycle(application);
        setDefaultUncaughtExceptionHandler();
    }

    private void registerActivityLifeCycle(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                liveActivityOrNull = activity;
            }

            @Override public void onActivityStarted(Activity activity) {}

            @Override public void onActivityResumed(Activity activity) {
                liveActivityOrNull = activity;
            }

            @Override public void onActivityPaused(Activity activity) {
                liveActivityOrNull = null;
            }

            @Override public void onActivityStopped(Activity activity) {}

            @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

            @Override public void onActivityDestroyed(Activity activity) {}
        });
    }

    private void setDefaultUncaughtExceptionHandler() {
        Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            //do you want to send the exception to a cloud repository?
            defaultHandler.uncaughtException(thread, ex);
        });
    }

    public Activity getLiveActivityOrNull() {
        return liveActivityOrNull;
    }
}
