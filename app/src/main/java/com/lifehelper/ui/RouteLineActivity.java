package com.lifehelper.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lifehelper.R;
import com.lifehelper.entity.RoutLineTabEntity;
import com.lifehelper.presenter.impl.RouteLinePresenterImpl;
import com.lifehelper.tools.T;
import com.lifehelper.view.RouteLineTabView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jsion on 16/3/16.
 */
public class RouteLineActivity extends BaseActivity implements RouteLineTabView {
    @Bind(R.id.toolbar_route_line)
    Toolbar mToolbar;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    private RouteLinePresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_line);
        init();
    }

    @Override
    protected void initData() {
        mPresenter = new RouteLinePresenterImpl(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        mPresenter.getRouteLineEntitys();
        mToolbar.setTitle(getResources().getString(R.string.route_line));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.mipmap.abc_ic_ab_back_mtrl_am_alpha));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void bindRouteLineTabs(List<RoutLineTabEntity> routLineTabEntities) {
        for (RoutLineTabEntity tabEntity : routLineTabEntities) {
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setText(tabEntity.getTabName());
            tab.setTag(tabEntity.getTabType());
            mTabLayout.addTab(tab);
            mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    T.show(RouteLineActivity.this, tab.getText() + "++++" + (int) tab.getTag(), 0);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

    }

    public static class TAB_TYPE {
        public static final int _BUS = 32;
        public static final int _WALK = 33;
        public static final int _CAR = 34;

    }
}
