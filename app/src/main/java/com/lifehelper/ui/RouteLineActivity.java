package com.lifehelper.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.lifehelper.R;
import com.lifehelper.app.MyConstance;
import com.lifehelper.entity.RoutLineTabEntity;
import com.lifehelper.presenter.impl.RouteLinePresenterImpl;
import com.lifehelper.tools.Logger;
import com.lifehelper.tools.ViewUtils;
import com.lifehelper.ui.fragment.ResultLineBusFragment;
import com.lifehelper.ui.fragment.ResultLineCarFragment;
import com.lifehelper.ui.fragment.ResultLineWalkFragment;
import com.lifehelper.ui.fragment.RouteLineLocationFragment;
import com.lifehelper.view.RouteLineTabView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jsion on 16/3/16.
 */
public class RouteLineActivity extends BaseActivity implements RouteLineTabView, RouteLineLocationFragment.OnGetFragmentValueListener {
    @Bind(R.id.toolbar_route_line)
    Toolbar mToolbar;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.tv_route_line_search)
    TextView mRouteLineSearch;

    private RouteLinePresenterImpl mPresenter;
    private ResultLineBusFragment mResultLineBusFragment;
    private ResultLineWalkFragment mResultLineWalkFragment;
    private ResultLineCarFragment mResultLineCarFragment;
    private RouteLineLocationFragment mRouteLineLocationFragment;
    private int mCurrentTabType;
    private LatLng mCurrentLatLng;

    @OnClick(R.id.tv_route_line_search)
    void routeLineSearch() {
        replaceResultFragment(mCurrentTabType);
        mRouteLineSearch.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_line);
        init();
    }

    @Override
    protected void initData() {
        getIntentData();
        mCurrentTabType = TAB_TYPE._BUS;
        mPresenter = new RouteLinePresenterImpl(this);
    }

    /**
     * get intent data
     */
    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mCurrentLatLng = bundle.getParcelable(MyConstance.CURRENT_LOCATION);
            }
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mRouteLineLocationFragment = new RouteLineLocationFragment();
        mResultLineBusFragment = new ResultLineBusFragment();
        mResultLineWalkFragment = new ResultLineWalkFragment();
        mResultLineCarFragment = new ResultLineCarFragment();

        FragmentManager mFM = getFragmentManager();
        FragmentTransaction mFT = mFM.beginTransaction();
        mFT.replace(R.id.fl_fragment_container, mRouteLineLocationFragment);
        mFT.commit();

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
            if (mRouteLineSearch.getVisibility() == View.VISIBLE) {
                onBackPressed();
            } else {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fl_fragment_container, mRouteLineLocationFragment);
                ft.commit();
                mRouteLineSearch.setVisibility(View.VISIBLE);
            }
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

                    switch ((int) tab.getTag()) {
                        case TAB_TYPE._BUS:
                            mCurrentTabType = (int) tab.getTag();
                            if (mRouteLineSearch.getVisibility() != View.VISIBLE) {
                                replaceResultFragment(mCurrentTabType);
                            }
                            break;
                        case TAB_TYPE._WALK:
                            mCurrentTabType = (int) tab.getTag();
                            if (mRouteLineSearch.getVisibility() != View.VISIBLE) {
                                replaceResultFragment(mCurrentTabType);
                            }
                            break;
                        case TAB_TYPE._CAR:
                            mCurrentTabType = (int) tab.getTag();
                            if (mRouteLineSearch.getVisibility() != View.VISIBLE) {
                                replaceResultFragment(mCurrentTabType);
                            }
                            break;
                    }

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

    /**
     * based on tab_type replace current result fragment
     *
     * @param tag
     */
    private void replaceResultFragment(int tag) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (tag) {
            case TAB_TYPE._BUS:
                ft.replace(R.id.fl_fragment_container, mResultLineBusFragment);
                break;
            case TAB_TYPE._WALK:
                ft.replace(R.id.fl_fragment_container, mResultLineWalkFragment);
                break;
            case TAB_TYPE._CAR:
                ft.replace(R.id.fl_fragment_container, mResultLineCarFragment);
                break;
        }
        ft.commit();
    }

    @Override
    public void startAddChanged(String startAdd) {
        Logger.e("出发地:" + startAdd);
    }

    @Override
    public void targetAddChanged(String targetAdd) {
        Logger.e("目的地:" + targetAdd);
    }

    public static class TAB_TYPE {
        public static final int _BUS = 32;
        public static final int _WALK = 33;
        public static final int _CAR = 34;

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN && isShouldHideInput(getCurrentFocus(), ev)) {
            ViewUtils.hideSolftInput(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
