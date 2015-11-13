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

package com.refineriaweb.app.presentation.foundation;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

@EActivity
public abstract class BaseToolbarActivity extends BaseCompatActivity {
    @ViewById protected Toolbar toolbar;

    @Override protected void initViews() {
        super.initViews();
        setUpToolbarOrDies();
        configureToolbar();
    }

    private void setUpToolbarOrDies() {
        if (toolbar == null) {
            String feedback = "To use BaseToolbarActivity as base class providing a Toolbar with id 'toolbar' is mandatory";
            throw new IllegalStateException(feedback);
        }

        setSupportActionBar(toolbar);
    }

    @StringRes protected String app_name;
    private void configureToolbar() {
        ActionBar actionBar = getSupportActionBar();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            boolean showBackKey = bundle.getBoolean(Behaviour.SHOW_BACK_KEY, Behaviour.SHOW_BACK_AS_DEFAULT);
            actionBar.setDisplayHomeAsUpEnabled(showBackKey);
            String title = bundle.getString(Behaviour.TITLE_KEY, app_name);
            getSupportActionBar().setTitle(title);
        } else {
            actionBar.setDisplayHomeAsUpEnabled(Behaviour.SHOW_BACK_AS_DEFAULT);
            getSupportActionBar().setTitle(app_name);
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public interface Behaviour {
        boolean SHOW_BACK_AS_DEFAULT = true;
        String SHOW_BACK_KEY = "show_back_key";
        String TITLE_KEY = "title_key";
    }
}
