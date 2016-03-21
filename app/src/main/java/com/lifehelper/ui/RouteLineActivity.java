package com.lifehelper.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.PlanNode;
import com.lifehelper.R;
import com.lifehelper.app.MyConstance;
import com.lifehelper.entity.MyPoiInfroWithLocationEntity;
import com.lifehelper.entity.RoutLinePlanots;
import com.lifehelper.entity.RoutLineTabEntity;
import com.lifehelper.presenter.impl.RouteLinePresenterImpl;
import com.lifehelper.tools.T;
import com.lifehelper.tools.ViewUtils;
import com.lifehelper.ui.fragment.ResultLineBusFragment;
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
    private RouteLineLocationFragment mRouteLineLocationFragment;
    private int mCurrentTabType;
    private BDLocation mCurrentBDLoation;
    private PlanNode mStartNode;
    private PlanNode mTargetNote;
    private RoutLinePlanots mRoutLinePlanots;
    private String mStartAddress;
    private String mTargetAddress;
    private String mWhereFrom;
    private MyPoiInfroWithLocationEntity mPoiInfroWithLocationEntity;

    @OnClick(R.id.tv_route_line_search)
    void routeLineSearch() {
        if (TextUtils.isEmpty(mStartAddress)) {
            T.show(this, getResources().getString(R.string.start_add_empty), 0);
        } else if (TextUtils.isEmpty(mTargetAddress)) {
            T.show(this, getResources().getString(R.string.target_add_empty), 0);
        } else {
            mRouteLineSearch.setVisibility(View.INVISIBLE);
            // set fragment data
            Bundle args = new Bundle();
            args.putParcelable(MyConstance.ROUTELINE_PLANNOTES, mRoutLinePlanots);

            mRoutLinePlanots.setTabType(mCurrentTabType);
            mResultLineBusFragment.setArguments(args);

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fl_fragment_container, mResultLineBusFragment);
            ft.commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_line);
        init();
    }

    @Override
    protected void initData() {
        mRoutLinePlanots = new RoutLinePlanots();

        getIntentData();
        mCurrentTabType = TAB_TYPE._BUS;
        mPresenter = new RouteLinePresenterImpl(this);
        mStartAddress = getResources().getString(R.string.my_address);
    }

    /**
     * get intent data
     */
    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mWhereFrom = bundle.getString(MyConstance.WHERE_FROM);
                if (!TextUtils.isEmpty(mWhereFrom) && mWhereFrom.equals(MainActivity.FROM_BOTTOM_SHEET)) {
                    mPoiInfroWithLocationEntity = bundle.getParcelable(MyConstance.CURRENT_POI_LOCATION);
                    mCurrentBDLoation = mPoiInfroWithLocationEntity.getmLocation();
                } else {
                    mCurrentBDLoation = bundle.getParcelable(MyConstance.CURRENT_LOCATION);
                }
                if (mCurrentBDLoation != null) {
                    mStartNode = PlanNode.withLocation(new LatLng(mCurrentBDLoation.getLatitude(), mCurrentBDLoation.getLongitude()));
                    mRoutLinePlanots.setStartPlanNode(mStartNode);
                }
            }
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mRouteLineLocationFragment = new RouteLineLocationFragment();
        mResultLineBusFragment = new ResultLineBusFragment();

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

        if (!TextUtils.isEmpty(mWhereFrom) && mWhereFrom.equals(MainActivity.FROM_BOTTOM_SHEET)) {
            Bundle args = new Bundle();
            args.putString(MyConstance.BOTTOM_SHEET_DESC, mPoiInfroWithLocationEntity.getmPoiInfoEntity().getPoiInfo().name);
            mRouteLineLocationFragment.setArguments(args);
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
    public void onBackPressed() {
        if (mRouteLineSearch.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        } else {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fl_fragment_container, mRouteLineLocationFragment);
            ft.commit();
            mRouteLineSearch.setVisibility(View.VISIBLE);
        }
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
                    mCurrentTabType = (int) tab.getTag();
                    if (mRouteLineSearch.getVisibility() != View.VISIBLE) {
                        mResultLineBusFragment.differentRoutePlan(mCurrentTabType);
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

    @Override
    public void startAddChanged(String startAdd) {
        mStartAddress = startAdd;
        if (startAdd.equals(getResources().getString(R.string.my_address))) {
            mStartNode = PlanNode.withLocation(new LatLng(mCurrentBDLoation.getLatitude(), mCurrentBDLoation.getLongitude()));
        } else {
            mStartNode = PlanNode.withCityNameAndPlaceName(mCurrentBDLoation.getCity(), startAdd);
        }
        mRoutLinePlanots.setStartPlanNode(mStartNode);
    }

    @Override
    public void targetAddChanged(String targetAdd) {
        mTargetAddress = targetAdd;
        mTargetNote = PlanNode.withCityNameAndPlaceName(mCurrentBDLoation.getCity(), targetAdd);
        mRoutLinePlanots.setTargetPlanNode(mTargetNote);
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
