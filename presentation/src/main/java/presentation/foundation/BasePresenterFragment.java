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
import android.view.View;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import javax.inject.Inject;

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

    protected ApplicationComponent getApplicationComponent() {
        return ((BaseCompatActivity)getActivity()).getApplicationComponent();
    }

    protected void showSnackBar(String title) {
        Snackbar.make(getView(), title, Snackbar.LENGTH_LONG)
                .show();
    }

    protected void showSnackBar(String title, String action, View.OnClickListener listener) {
        Snackbar.make(getView(), title, Snackbar.LENGTH_LONG)
                .setAction(action, listener)
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
