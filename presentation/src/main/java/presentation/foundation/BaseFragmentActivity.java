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
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.Serializable;

import base.app.android.R;
import butterknife.Bind;
import presentation.internal.di.ApplicationComponent;

/**
 * Created by miguel on 04/03/16.
 */
public class BaseFragmentActivity extends AppCompatActivity {
    @Nullable @Bind(R.id.appBarLayout) protected AppBarLayout appBarLayout;
    @Nullable @Bind(R.id.toolbar) protected Toolbar toolbar;
    protected String app_name;
    private MaterialDialog materialDialog;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_name = getString(R.string.app_name);
        initViews();
    }

    public BaseApp getBaseApp() {
        return ((BaseApp)getApplication());
    }

    public ApplicationComponent getApplicationComponent() {
        return getBaseApp().getApplicationComponent();
    }

    private void initViews() {
        getWindow().getDecorView().post(() -> configureToolbar(toolbar, appBarLayout));

        Bundle bundle = getIntent().getExtras();
        if (bundle == null || bundle.getSerializable(Behaviour.FRAGMENT_CLASS_KEY) == null) {
            Log.w("BaseFragmentActivity", "When using HostCommonActivity you need to supply " +
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

    private void configureToolbar(Toolbar toolbar, @Nullable AppBarLayout appBarLayout) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        boolean showToolbar = Behaviour.SHOW_TOOLBAR_AS_DEFAULT;

        if (actionBar != null) {
            Bundle bundle = getIntent().getExtras();

            if (bundle != null) {
                boolean showBackKey = bundle.getBoolean(Behaviour.SHOW_BACK_KEY, Behaviour.SHOW_BACK_AS_DEFAULT);
                showToolbar = bundle.getBoolean(Behaviour.SHOW_TOOLBAR, showToolbar);
                actionBar.setDisplayHomeAsUpEnabled(showBackKey);
                String title = bundle.getString(Behaviour.TITLE_KEY, getString(R.string.app_name));
                getSupportActionBar().setTitle(title);
            } else {
                actionBar.setDisplayHomeAsUpEnabled(Behaviour.SHOW_BACK_AS_DEFAULT);
                getSupportActionBar().setTitle(app_name);
            }
        }

        setStatusBarColor();

        if (appBarLayout != null)
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

    public void showLoading() {
        materialDialog =  new MaterialDialog.Builder(this)
                .titleColorRes(R.color.colorPrimaryDark)
                .cancelable(false)
                .contentColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .widgetColorRes(R.color.colorPrimaryDark)
                .title(app_name)
                .content(getString(R.string.loading))
                .progress(true, 0)
                .show();
    }

    public void hideLoading() {
        if (materialDialog != null) materialDialog.dismiss();
    }

    @Override public void onBackPressed() {
        BasePresenterFragment fragment = getCurrentPresenterFragment();
        BackButtonListener listener = fragment != null && fragment instanceof BackButtonListener ? (BackButtonListener) fragment : null;

        if (listener == null) {
            super.onBackPressed();
            return;
        }

        if (listener.onBackPressed()) super.onBackPressed();
    }

    /***
     * BasePresenterFragment can implement this interface to be notified when user performs a back action.
     */
    public interface BackButtonListener {
        /***
         * @return true if activity must handle back action, as removing itself from the stack
         */
        boolean onBackPressed();
    }
}