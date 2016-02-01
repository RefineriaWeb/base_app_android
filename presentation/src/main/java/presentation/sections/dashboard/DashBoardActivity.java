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

package presentation.sections.dashboard;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.List;

import javax.inject.Inject;

import base.app.android.R;
import domain.sections.dashboard.DashboardPresenter;
import domain.sections.dashboard.DashboardView;
import domain.sections.dashboard.ItemMenu;
import presentation.foundation.SingleFragmentActivity;
import presentation.sections.user_demo.detail.UserFragment_;
import presentation.sections.user_demo.list.UsersFragment_;
import presentation.sections.user_demo.search.SearchUserFragment_;
import presentation.utilities.recyclerview_adapter.RecyclerViewAdapter;
import rx.Observable;
import rx.Subscription;

@EActivity(R.layout.dashboard_activity)
public class DashBoardActivity extends SingleFragmentActivity implements DashboardView {
    @Inject protected DashboardPresenter presenter;
    @Bean protected ItemMenuDashboardAdapter adapter;

    @ViewById protected RecyclerView rv_menu_items;
    @ViewById protected DrawerLayout drawer_layout;

    private ActionBarDrawerToggle drawerToggle;

    @Override protected void init() {
        super.init();
        getApplicationComponent().inject(this);
    }

    @Override protected void initViews() {
        super.initViews();
        setUpDrawerToggle();
        setUpRecyclerView();

        presenter.attachView(this);
    }

    private void setUpDrawerToggle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.app_name, R.string.app_name) {
            @Override public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            @Override public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer_layout.setDrawerListener(drawerToggle);
    }

    private void setUpRecyclerView() {
        rv_menu_items.setHasFixedSize(true);
        rv_menu_items.setLayoutManager(new LinearLayoutManager(this));

        adapter.addListener((itemMenu, itemMenuViewGroup) -> {
            drawer_layout.closeDrawer(rv_menu_items);
            presenter.setSelectedItemMenu(itemMenu);
        });
        rv_menu_items.setAdapter(adapter);
    }

    @Override public Subscription loadItemsMenu(Observable<List<ItemMenu>> oItems) {
        return oItems.subscribe(items -> adapter.addAll(items));
    }

    @StringRes protected String users;
    @Override public void showUsers() {
        replaceFragmentIfItIsNotCurrentDisplayed(UsersFragment_.class);
        setTitle(users);
    }

    @StringRes protected String user;
    @Override public void showUser() {
        replaceFragmentIfItIsNotCurrentDisplayed(UserFragment_.class);
        setTitle(user);
    }

    @StringRes protected String find_user;
    @Override public void showUserSearch() {
        replaceFragmentIfItIsNotCurrentDisplayed(SearchUserFragment_.class);
        setTitle(find_user);
    }

    @Override protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) return true;
        else if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @EBean static protected class ItemMenuDashboardAdapter extends RecyclerViewAdapter<ItemMenu, ItemMenuViewGroup> {
        @RootContext protected Context context;

        @Override protected ItemMenuViewGroup onCreateItemView(ViewGroup parent, int viewType) {
            return ItemMenuViewGroup_.build(context);
        }
    }
}
