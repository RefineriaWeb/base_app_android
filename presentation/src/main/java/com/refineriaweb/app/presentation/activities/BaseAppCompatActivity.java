package com.refineriaweb.app.presentation.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.refineriaweb.app.presentation.utilities.AndroidApplication;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;

@EActivity
public abstract class BaseAppCompatActivity extends AppCompatActivity {
    @App protected AndroidApplication app;

    @Override protected void onResume() {
        super.onResume();
        app.setCurrentActivity(this);
    }

    @Override protected void onPause() {
        super.onPause();
        clearReferences();
    }

    private void clearReferences(){
        Activity currActivity = app.getCurrentActivity();
        if (currActivity != null && currActivity.equals(this))
            app.setCurrentActivity(null);
    }
}
