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

import java.util.List;

import javax.inject.Inject;

import base.app.android.R;
import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import domain.sections.dashboard.DashboardPresenter;
import domain.sections.dashboard.DashboardView;
import domain.sections.dashboard.ItemMenu;
import presentation.foundation.BaseFragmentActivity;
import presentation.sections.user_demo.detail.UserFragment;
import presentation.sections.user_demo.list.UsersFragment;
import presentation.sections.user_demo.search.SearchUserFragment;
import presentation.utilities.recyclerview_adapter.RecyclerViewAdapter;
import rx.Observable;
import rx.Subscription;

public class DashBoardActivity extends BaseFragmentActivity implements DashboardView {
    @Inject protected DashboardPresenter presenter;
    @Bind(R.id.rv_menu_items) protected RecyclerView rv_menu_items;
    @Bind(R.id.drawer_layout) protected DrawerLayout drawer_layout;
    private ItemMenuDashboardAdapter adapter;
    private ActionBarDrawerToggle drawerToggle;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        getApplicationComponent().inject(this);
        ButterKnife.bind(this);
        initViews();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        drawer_layout.removeDrawerListener(drawerToggle);
    }

    private void initViews() {
        adapter = new ItemMenuDashboardAdapter(this);
        setUpDrawerToggle();
        setUpRecyclerView();
        presenter.attachView(this);
    }

    private void setUpDrawerToggle() {
        setSupportActionBar(toolbar);
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

        drawer_layout.addDrawerListener(drawerToggle);
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

    @BindString(R.string.users) protected String users;
    @Override public void showUsers() {
        replaceFragmentIfItIsNotCurrentDisplayed(UsersFragment.class);
        setTitle(users);
    }

    @BindString(R.string.user) protected String user;
    @Override public void showUser() {
        replaceFragmentIfItIsNotCurrentDisplayed(UserFragment.class);
        setTitle(user);
    }

    @BindString(R.string.find_user) protected String find_user;
    @Override public void showUserSearch() {
        replaceFragmentIfItIsNotCurrentDisplayed(SearchUserFragment.class);
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

    static private class ItemMenuDashboardAdapter extends RecyclerViewAdapter<ItemMenu, ItemMenuViewGroup> {
        private Context context;

        public ItemMenuDashboardAdapter(Context context) {
            this.context = context;
        }

        @Override protected ItemMenuViewGroup onCreateItemView(ViewGroup parent, int viewType) {
            return new ItemMenuViewGroup(context);
        }
    }
}
