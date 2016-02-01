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

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringRes;

import javax.inject.Inject;

import base.app.android.R;
import domain.foundation.BaseView;
import domain.foundation.Presenter;
import presentation.internal.di.ApplicationComponent;

@EFragment
public abstract class BasePresenterFragment<P extends Presenter> extends Fragment implements BaseView {
    @Inject protected P presenter;

    @AfterInject protected void init() {}

    @AfterViews protected void initViews() {
        presenter.attachView(this);
    }

    @Override public void onResume() {
        super.onResume();
        presenter.resumeView();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((SingleFragmentActivity)getActivity()).getApplicationComponent();
    }

    @StringRes protected String app_name, loading;
    @ColorRes protected int colorPrimaryDark;
    private MaterialDialog materialDialog;
    protected void showLoading() {
        materialDialog =  new MaterialDialog.Builder(getActivity())
                .titleColorRes(R.color.colorPrimaryDark)
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

    public void showToast(String title) {
        Toast.makeText(getActivity(), title, Toast.LENGTH_LONG)
                .show();
    }

    public void showSnackBar(String title) {
        Snackbar.make(getView(), title, Snackbar.LENGTH_LONG)
                .show();
    }

    protected void replaceFragment(int id, BasePresenterFragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction()
                .replace(id, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}
