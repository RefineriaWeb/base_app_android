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

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.refineriaweb.app.presentation.internal.di.ApplicationComponent;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import javax.inject.Inject;

import app.refineriaweb.com.domain.foundation.BaseView;
import app.refineriaweb.com.domain.foundation.Presenter;

@EFragment
public abstract class BaseFragment<P extends Presenter> extends Fragment implements BaseView {
    @Inject protected P presenter;

    @AfterInject protected void init() {}
    
    @AfterViews protected void initViews() {
        presenter.attachView(this);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((BaseCompatActivity)getActivity()).getApplicationComponent();
    }

    protected void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}
