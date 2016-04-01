package com.lifehelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.baidu.location.BDLocation;
import com.lifehelper.R;
import com.lifehelper.app.MyConstance;
import com.lifehelper.entity.JokeEntity;
import com.lifehelper.entity.JokeImgEntity;
import com.lifehelper.entity.MovieEntity;
import com.lifehelper.entity.MovieRecentEntity;
import com.lifehelper.presenter.impl.JuHeJokePresenterImpl;
import com.lifehelper.tools.Logger;
import com.lifehelper.tools.T;
import com.lifehelper.ui.customwidget.LoadingDialog;
import com.lifehelper.view.JuHeJokeView;
import com.lifehelper.view.JuHeMovieView;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by jsion on 16/3/29.
 */
public class WhoActivity extends BaseActivity implements JuHeMovieView, JuHeJokeView {
    private static final String TAG = "WhoActivity";
    @Bind(R.id.toolbar_who)
    Toolbar mToolbar;
    @Bind(R.id.rlv_who_joke)
    RecyclerView mJokeData;
    @Bind(R.id.srl_load_joke)
    SwipeRefreshLayout mLoadJoke;

    private BDLocation mCurrentBdLocation;
    private JuHeJokePresenterImpl mJokePresenter;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who);
        init();
    }

    @Override
    protected void initData() {
        loadingDialog = new LoadingDialog(this, false);
        mJokePresenter = new JuHeJokePresenterImpl(this);
        getIntentData();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        mToolbar.setTitle(getResources().getString(R.string.who));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.mipmap.abc_ic_ab_back_mtrl_am_alpha));
        }
        mJokeData.setLayoutManager(new LinearLayoutManager(this));
        mLoadJoke.setColorSchemeColors(getResources().getColor(R.color.skin_background_black)
                , (getResources().getColor(R.color.skin_colorAccent_Amber))
                , (getResources().getColor(R.color.skin_colorAccent_blue))
                , (getResources().getColor(R.color.skin_colorAccent_mred)));

        mLoadJoke.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        mLoadJoke.post(new Runnable() {
            @Override
            public void run() {
                mLoadJoke.setRefreshing(true);
            }
        });

        mJokePresenter.getJokeEntity(1, 20);
        mJokePresenter.getJokeImgEntity(2, 10);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mCurrentBdLocation = extras.getParcelable(MyConstance.CURRENT_LOCATION);
            }
        }
    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void bindJuHeJoke(JokeEntity jokeEntity) {
        Logger.e(jokeEntity.toString() + "======获取jokeContent全部列表");
        T.show(this, "joke加载全部列表完成", 0);
    }

    @Override
    public void bindJHeJokeImg(JokeImgEntity jokeImgEntity) {
        Logger.e(jokeImgEntity.toString() + "======获取全部jokeImg列表");
        T.show(this, "jokeImg加载全部列表完成", 0);
    }

    @Override
    public void bindJuHeSearchMovie(MovieEntity movieEntity) {
        MovieEntity.ResultBean result = movieEntity.getResult();
        Logger.e(result.getAct() + result.getTitle() + result.getYear());
    }

    @Override
    public void bindJHeRecentMovies(MovieRecentEntity movieRecentEntity) {
        MovieRecentEntity.MovieDataType data = movieRecentEntity.getResult().getData();
        Logger.e(data + "======获取全部列表");
        T.show(this, "加载全部列表完成", 0);
    }


    @Override
    public void showErrorMessage(String error) {
        loadingDialog.dismiss();
    }

    @Override
    public void dismissLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void completed() {
        loadingDialog.dismiss();
    }

}
