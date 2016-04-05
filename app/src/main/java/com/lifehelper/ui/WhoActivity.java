package com.lifehelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lifehelper.R;
import com.lifehelper.app.MyConstance;
import com.lifehelper.entity.JokeEntity;
import com.lifehelper.entity.JokeEntiyForUI;
import com.lifehelper.entity.JokeImgEntity;
import com.lifehelper.entity.JokeImgEntityForUI;
import com.lifehelper.entity.MovieEntity;
import com.lifehelper.entity.MovieRecentEntity;
import com.lifehelper.presenter.impl.JuHeJokePresenterImpl;
import com.lifehelper.tools.Logger;
import com.lifehelper.tools.T;
import com.lifehelper.ui.customwidget.LoadingDialog;
import com.lifehelper.view.JuHeJokeView;
import com.lifehelper.view.JuHeMovieView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by jsion on 16/3/29.
 */
public class WhoActivity extends BaseActivity implements JuHeMovieView, JuHeJokeView {
    private static final String TAG = "WhoActivity";
    /**
     * each page max count must <= 20 and >= 1
     */
    private static final int PAG_COUNT_NUM = 2;
    @Bind(R.id.toolbar_who)
    Toolbar mToolbar;
    @Bind(R.id.rlv_who_joke)
    RecyclerView mJokeData;
    @Bind(R.id.srl_load_joke)
    SwipeRefreshLayout mLoadJoke;
    @Bind(R.id.tv_who_search)
    TextView mChangeJoke;

    @OnClick(R.id.tv_who_search)
    void changeJokeContent() {
        if (mCurrentType == JOKE_TYPE._CONTENT) {
            mCurrentType = JOKE_TYPE._IMG;
            refreshData();
        } else if (mCurrentType == JOKE_TYPE._IMG) {
            mCurrentType = JOKE_TYPE._CONTENT;
            refreshData();
        }
    }

    private int mCurrentType;
    private int mCurrentDir;
    private int mContentPage;
    private int mIMGPage;
    private BDLocation mCurrentBdLocation;
    private JuHeJokePresenterImpl mJokePresenter;
    private LoadingDialog loadingDialog;

    private List<JokeEntiyForUI> mJokeEntities;
    private List<JokeImgEntityForUI> mJokeImgEntities;
    private JokeContentAdapter mJokeContentAdapter;
    private JokeIMGAdapter mJokeIMGAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who);
        init();
    }

    @Override
    protected void initData() {
        mCurrentType = JOKE_TYPE._IMG;
        mCurrentDir = TYPE_DIR._START;
        mContentPage = 1;
        mIMGPage = 1;
        loadingDialog = new LoadingDialog(this, false);
        mJokePresenter = new JuHeJokePresenterImpl(this);
        mJokeEntities = new ArrayList<>();
        mJokeImgEntities = new ArrayList<>();
        mJokeContentAdapter = new JokeContentAdapter();
        mJokeIMGAdapter = new JokeIMGAdapter();
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
        mJokeData.setItemAnimator(new DefaultItemAnimator());
        mJokeData.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager m = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // get last visible item
                    int lastVisibleItem = m.findLastCompletelyVisibleItemPosition();
                    // get all item count
                    int itemCount = m.getItemCount();
                    if (lastVisibleItem == itemCount - 1) {
                        mCurrentDir = TYPE_DIR._END;
                        loadingDialog.show();
                        refreshData();
                    }
                }
            }
        });
        mLoadJoke.setColorSchemeColors(getResources().getColor(R.color.skin_background_black)
                , (getResources().getColor(R.color.skin_colorAccent_Amber))
                , (getResources().getColor(R.color.skin_colorAccent_blue))
                , (getResources().getColor(R.color.skin_colorAccent_mred)));

        mLoadJoke.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentDir = TYPE_DIR._START;
                refreshData();
            }
        });
        mLoadJoke.post(new Runnable() {
            @Override
            public void run() {
                mLoadJoke.setRefreshing(true);
            }
        });

        refreshData();
    }

    /**
     * refresh joke data
     */
    private void refreshData() {
        if (mLoadJoke != null) {
            mLoadJoke.setRefreshing(true);
            if (mCurrentType == JOKE_TYPE._CONTENT) {
                mJokeData.setAdapter(mJokeContentAdapter);
                mJokePresenter.getJokeEntity(mContentPage, PAG_COUNT_NUM);
            } else if (mCurrentType == JOKE_TYPE._IMG) {
                mJokeData.setAdapter(mJokeIMGAdapter);
                mJokePresenter.getJokeImgEntity(mIMGPage, PAG_COUNT_NUM);
            }
        }
    }

    private void stopRefresh() {
        if (mLoadJoke.isRefreshing()) {
            mLoadJoke.setRefreshing(false);
        }
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
        stopRefresh();
        if (jokeEntity != null) {
            if (jokeEntity.getError_code() == 0) {
                JokeEntity.ResultBean result = jokeEntity.getResult();
                if (result != null) {
                    List<JokeEntity.ResultBean.DataBean> data = result.getData();
                    int size = 0;
                    if (data != null) {
                        size = mJokeEntities.size();
                        for (int i = 0; i < data.size(); i++) {
                            JokeEntiyForUI jokeEntiyForUI = generateJokeContentUI(data.get(i));
                            if (mCurrentDir == TYPE_DIR._START) {
                                mJokeEntities.add(0, jokeEntiyForUI);
                                mJokeContentAdapter.notifyDataSetChanged();
                            } else if (mCurrentDir == TYPE_DIR._END) {
                                mJokeEntities.add(mJokeContentAdapter.getItemCount() - 1, jokeEntiyForUI);
                            }
                        }
                    }
                    mJokeContentAdapter.notifyDataSetChanged();
                    if (mCurrentDir == TYPE_DIR._END) {
                        mJokeData.scrollToPosition(size);
                    }
                    mContentPage++;
                }
            } else {
                // TODO: 16/4/5 based on the error code show the current notice
                T.show(this, "error_code=" + jokeEntity.getError_code(), 0);
            }
        } else {
            T.show(this, "内部错误", 0);
        }
    }

    /**
     * server model need generate UI model
     * better do not modify the server source data
     *
     * @param dataBean
     * @return jokeEntiyForUI
     */
    private JokeEntiyForUI generateJokeContentUI(JokeEntity.ResultBean.DataBean dataBean) {
        JokeEntiyForUI jokeEntiyForUI = new JokeEntiyForUI();
        jokeEntiyForUI.setContent(dataBean.getContent());
        jokeEntiyForUI.setHashId(dataBean.getHashId());
        jokeEntiyForUI.setUnixtime(dataBean.getUnixtime());
        jokeEntiyForUI.setUpdatetime(dataBean.getUpdatetime());
        return jokeEntiyForUI;
    }

    /**
     * server model need generate UI model
     * better do not modify the server source data
     *
     * @param dataBean
     * @return jokeImgEntityForUI
     */
    private JokeImgEntityForUI generateJokeIMGUI(JokeImgEntity.ResultBean.DataBean dataBean) {
        JokeImgEntityForUI jokeImgEntityForUI = new JokeImgEntityForUI();
        jokeImgEntityForUI.setUpdatetime(dataBean.getUpdatetime());
        jokeImgEntityForUI.setUnixtime(dataBean.getUnixtime());
        jokeImgEntityForUI.setHashId(dataBean.getHashId());
        jokeImgEntityForUI.setContent(dataBean.getContent());
        jokeImgEntityForUI.setUrl(dataBean.getUrl());
        return jokeImgEntityForUI;
    }

    @Override
    public void bindJHeJokeImg(JokeImgEntity jokeImgEntity) {
        stopRefresh();
        if (jokeImgEntity != null) {
            if (jokeImgEntity.getError_code() == 0) {
                JokeImgEntity.ResultBean result = jokeImgEntity.getResult();
                if (result != null) {
                    int size = 0;
                    List<JokeImgEntity.ResultBean.DataBean> data = result.getData();
                    if (data != null) {
                        size = mJokeImgEntities.size();
                        for (int i = 0; i < data.size(); i++) {
                            JokeImgEntityForUI jokeImgEntityForUI = generateJokeIMGUI(data.get(i));
                            if (mCurrentDir == TYPE_DIR._START) {
                                mJokeImgEntities.add(0, jokeImgEntityForUI);
                            } else if (mCurrentDir == TYPE_DIR._END) {
                                mJokeImgEntities.add(mJokeIMGAdapter.getItemCount() - 1, jokeImgEntityForUI);
                            }
                        }
                    }
                    mJokeIMGAdapter.notifyDataSetChanged();
                    if (mCurrentDir == TYPE_DIR._END) {
                        mJokeData.scrollToPosition(size);
                    }
                    mIMGPage++;
                }
            } else {
                // TODO: 16/4/5 based on the error code show the current notice
                T.show(this, "error_code=" + jokeImgEntity.getError_code(), 0);
            }
        } else {
            T.show(this, "内部错误", 0);
        }
    }


    @Override
    public void bindJuHeSearchMovie(MovieEntity movieEntity) {
        stopRefresh();
        MovieEntity.ResultBean result = movieEntity.getResult();
        Logger.e(result.getAct() + result.getTitle() + result.getYear());
    }

    @Override
    public void bindJHeRecentMovies(MovieRecentEntity movieRecentEntity) {
        stopRefresh();
        MovieRecentEntity.MovieDataType data = movieRecentEntity.getResult().getData();
    }


    @Override
    public void showErrorMessage(String error) {
        stopRefresh();
        loadingDialog.dismiss();
    }

    @Override
    public void dismissLoading() {
        stopRefresh();
        loadingDialog.dismiss();
    }

    @Override
    public void completed() {
        stopRefresh();
        loadingDialog.dismiss();
    }

    class JokeContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = new JokeContentVH(LayoutInflater.from(WhoActivity.this)
                    .inflate(R.layout.item_joke_content, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            JokeContentVH jokeContentVH = (JokeContentVH) holder;
            JokeEntiyForUI jokeEntiyForUI = mJokeEntities.get(position);
            if (jokeEntiyForUI != null) {
                String content = jokeEntiyForUI.getContent();
                String updatetime = jokeEntiyForUI.getUpdatetime();
                if (!TextUtils.isEmpty(content)) {
                    jokeContentVH.jokeContent.setText(content);
                }
                if (!TextUtils.isEmpty(updatetime)) {
                    jokeContentVH.jokeContentUpdateTime.setText(updatetime);
                }
            }
        }

        @Override
        public int getItemCount() {
            return mJokeEntities.size();
        }

        public void addItem(JokeEntiyForUI currentData, int position) {
            mJokeEntities.add(position, currentData);
            notifyItemInserted(position);
        }

        class JokeContentVH extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_item_joke_content)
            TextView jokeContent;
            @Bind(R.id.tv_item_joke_update_time)
            TextView jokeContentUpdateTime;

            public JokeContentVH(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


    class JokeIMGAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = new JokeIMGVH(LayoutInflater.from(WhoActivity.this)
                    .inflate(R.layout.item_joke_image, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            JokeIMGVH jokeIMGVH = (JokeIMGVH) holder;
            JokeImgEntityForUI jokeImgEntityForUI = mJokeImgEntities.get(position);
            if (jokeImgEntityForUI != null) {
                String content = jokeImgEntityForUI.getContent();
                String updatetime = jokeImgEntityForUI.getUpdatetime();
                String url = jokeImgEntityForUI.getUrl();
                if (!TextUtils.isEmpty(content)) {
                    jokeIMGVH.jokeImgContent.setText(content);
                }
                if (!TextUtils.isEmpty(updatetime)) {
                    jokeIMGVH.jokeImgContentUpdateTime.setText(updatetime);
                }
                if (!TextUtils.isEmpty(url)) {
                    Glide.with(WhoActivity.this)
                            .load(url)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .crossFade()
                            .placeholder(R.mipmap.bg_de)
                            .error(R.mipmap.bg_no_pic)
                            .into(jokeIMGVH.jokeImage);

                }
            }
        }

        @Override
        public int getItemCount() {
            return mJokeImgEntities.size();
        }

        public void addItem(JokeImgEntityForUI currentData, int position) {
            mJokeImgEntities.add(position, currentData);
            notifyItemInserted(position);
        }

        class JokeIMGVH extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_item_joke_imag_content)
            TextView jokeImgContent;
            @Bind(R.id.tv_item_joke_image_update_time)
            TextView jokeImgContentUpdateTime;
            @Bind(R.id.iv_item_joke_img)
            ImageView jokeImage;

            public JokeIMGVH(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    static class JOKE_TYPE {
        public static final int _CONTENT = 88;
        public static final int _IMG = 89;
    }

    static class TYPE_DIR {
        public static final int _START = 43;
        public static final int _END = 44;
    }

}
