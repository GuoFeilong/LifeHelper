package com.lifehelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.baidu.location.BDLocation;
import com.lifehelper.R;
import com.lifehelper.app.MyConstance;
import com.lifehelper.entity.MovieEntity;
import com.lifehelper.presenter.impl.JuHeMoviePresenterImpl;
import com.lifehelper.tools.Logger;
import com.lifehelper.ui.customwidget.LoadingDialog;
import com.lifehelper.view.JuHeMovieView;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by jsion on 16/3/29.
 */
public class WhoActivity extends BaseActivity implements JuHeMovieView {
    @Bind(R.id.toolbar_who)
    Toolbar mToolbar;
    private BDLocation mCurrentBdLocation;
    private JuHeMoviePresenterImpl mMoviePresenter;
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
        mMoviePresenter = new JuHeMoviePresenterImpl(this);
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

        mMoviePresenter.getSearchMovie(mCurrentBdLocation.getCity(), "灌篮高手");
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
    public void bindJuHeSearchMovie(MovieEntity movieEntity) {
        MovieEntity.ResultBean result = movieEntity.getResult();
        Logger.e(result.getAct() + result.getTitle() + result.getYear());
    }

    @Override
    public void bindJHeRecentMovies() {

    }

    @Override
    public void showErrorMessage() {
        loadingDialog.dismiss();
    }

    @Override
    public void dismissLoading() {
        loadingDialog.dismiss();
    }
}
