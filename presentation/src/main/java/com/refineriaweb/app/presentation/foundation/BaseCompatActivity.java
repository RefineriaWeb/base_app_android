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

import android.support.v7.app.AppCompatActivity;

import com.refineriaweb.app.presentation.internal.di.ApplicationComponent;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

import app.refineriaweb.com.domain.sections.Wireframe;

@EActivity
public abstract class BaseCompatActivity extends AppCompatActivity  {
    @Inject protected Wireframe wireframe;

    @AfterInject protected void init() {
        getApplicationComponent().inject(this);
    }

    @AfterViews protected void initViews() {}

    public BaseApp getBaseApp() {
        return ((BaseApp)getApplication());
    }

    public ApplicationComponent getApplicationComponent() {
        return getBaseApp().getApplicationComponent();
    }
}
