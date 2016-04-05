package com.lifehelper.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifehelper.R;
import com.lifehelper.tools.ViewUtils;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jsion on 16/4/5.
 */
public class StartActivity extends BaseActivity {
    @Bind(R.id.tv_start_app_name)
    TextView mStartAppName;
    @Bind(R.id.iv_start_map)
    ImageView mStartMap;
    @Bind(R.id.iv_start_logo)
    ImageView mStartLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        initMapAnim();
    }

    private void initMapAnim() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mStartMap, "scaleX", 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mStartMap, "scaleY", 0f, 1f);
        ObjectAnimator alph = ObjectAnimator.ofFloat(mStartMap, "alpha", 0f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, alph);
        animatorSet.setDuration(2000);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                initLogoAnim();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.start();
    }

    private void initLogoAnim() {
        mStartLogo.setVisibility(View.VISIBLE);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mStartLogo, "translationY", -600, 0);
        translationY.setDuration(1500);
        translationY.setInterpolator(new BounceInterpolator());
        translationY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mStartAppName.setVisibility(View.VISIBLE);
                Observable.interval(2, 0, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Long>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                ViewUtils.changeActivity(StartActivity.this, MainActivity.class);
                                finish();
                                this.unsubscribe();
                            }
                        });
            }
        });

        translationY.start();
    }
}
