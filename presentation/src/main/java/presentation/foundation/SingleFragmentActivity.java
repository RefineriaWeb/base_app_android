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

package presentation.foundation;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringRes;

import java.io.Serializable;

import base.app.android.R;
import presentation.internal.di.ApplicationComponent;

/**
 * Created by victor on 23/11/15.
 */
@EActivity(R.layout.host_single_fragment_activity)
public class SingleFragmentActivity extends AppCompatActivity {
    @ViewById protected AppBarLayout appBarLayout;
    @ViewById protected Toolbar toolbar;
    @StringRes protected String app_name;

    public BaseApp getBaseApp() {
        return ((BaseApp)getApplication());
    }

    public ApplicationComponent getApplicationComponent() {
        return getBaseApp().getApplicationComponent();
    }

    @AfterInject protected void init() {
        getApplicationComponent().inject(this);
    }

    @AfterViews protected void initViews() {
        if (toolbar != null) configureToolbar();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Log.w("SingleFragmentActivity", "When using HostCommonActivity you need to supply " +
                    "a valid type BaseFragment class by extra argument in the intent" +
                    "with key " + Behaviour.FRAGMENT_CLASS_KEY);
            return;
        }

        Serializable serializable = bundle.getSerializable(Behaviour.FRAGMENT_CLASS_KEY);
        Class<BasePresenterFragment> clazz = (Class<BasePresenterFragment>) serializable;

        BasePresenterFragment basePresenterFragment = replaceFragment(clazz);
        Bundle bundleFragment = bundle.getBundle(Behaviour.BUNDLE_FOR_FRAGMENT);
        basePresenterFragment.setArguments(bundleFragment);
    }

    private void setUpToolbarOrDies() {
        if (toolbar == null) {
            String feedback = "To use BaseToolbarActivity as base class providing a Toolbar with id 'toolbar' is mandatory";
            throw new IllegalStateException(feedback);
        }

    }

    protected <T extends BasePresenterFragment> BasePresenterFragment replaceFragmentIfItIsNotCurrentDisplayed(Class<T> clazz) {
        BasePresenterFragment current = getCurrentPresenterFragment();
        if (current != null && current.getClass() == clazz) return current;
        return replaceFragment(clazz);
    }

    protected <T extends BasePresenterFragment> BasePresenterFragment replaceFragment(Class<T> clazz) {
        try {
            BasePresenterFragment fragment = clazz.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, fragment).commit();
            return fragment;
        } catch (InstantiationException e) {
            throw new IllegalStateException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public interface Behaviour {
        String FRAGMENT_CLASS_KEY = "fragment_class_key";
        String BUNDLE_FOR_FRAGMENT = "bundle_for_fragment";
        boolean SHOW_BACK_AS_DEFAULT = true;
        String SHOW_BACK_KEY = "show_back_key";
        String TITLE_KEY = "title_key";
        boolean SHOW_TOOLBAR_AS_DEFAULT = true;
        String SHOW_TOOLBAR = "show_toolbar";
    }

    public BasePresenterFragment getCurrentPresenterFragment() {
        return (BasePresenterFragment) getSupportFragmentManager().findFragmentById(R.id.fl_fragment);
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        Bundle bundle = getIntent().getExtras();
        boolean showToolbar = Behaviour.SHOW_TOOLBAR_AS_DEFAULT;

        if (bundle != null) {
            boolean showBackKey = bundle.getBoolean(Behaviour.SHOW_BACK_KEY, Behaviour.SHOW_BACK_AS_DEFAULT);
            showToolbar = bundle.getBoolean(Behaviour.SHOW_TOOLBAR, showToolbar);
            actionBar.setDisplayHomeAsUpEnabled(showBackKey);
            String title = bundle.getString(Behaviour.TITLE_KEY, app_name);
            getSupportActionBar().setTitle(title);
        } else {
            actionBar.setDisplayHomeAsUpEnabled(Behaviour.SHOW_BACK_AS_DEFAULT);
            getSupportActionBar().setTitle(app_name);
        }

        setStatusBarColor();
        appBarLayout.setVisibility(showToolbar ? View.VISIBLE : View.GONE);
    }

    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    @StringRes protected String loading;
    @ColorRes protected int colorPrimaryDark;
    private MaterialDialog materialDialog;
    protected void showLoading() {
        materialDialog =  new MaterialDialog.Builder(this)
                .titleColorRes(R.color.colorPrimaryDark)
                .cancelable(false)
                .contentColor(colorPrimaryDark)
                .widgetColorRes(R.color.colorPrimaryDark)
                .title(app_name)
                .content(loading)
                .progress(true, 0)
                .show();
    }

    protected void hideLoading() {
        if (materialDialog != null) materialDialog.dismiss();
    }
}