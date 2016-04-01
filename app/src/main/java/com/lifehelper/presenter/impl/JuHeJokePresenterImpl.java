package com.lifehelper.presenter.impl;

import com.lifehelper.entity.JokeEntity;
import com.lifehelper.entity.JokeImgEntity;
import com.lifehelper.model.JokeModel;
import com.lifehelper.presenter.JuHeJokePresenter;
import com.lifehelper.service.JuHeJokeService;
import com.lifehelper.view.JuHeJokeView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by jsion on 16/4/1.
 */
public class JuHeJokePresenterImpl implements JuHeJokePresenter {
    private JuHeJokeView juHeJokeView;
    private JokeModel jokeModel;
    private JuHeJokeService juHeJokeService;

    public JuHeJokePresenterImpl(JuHeJokeView juHeJokeView) {
        jokeModel = new JokeModel();
        juHeJokeService = jokeModel.getJuHeJokeService();
        this.juHeJokeView = juHeJokeView;
    }

    @Override
    public void getJokeEntity(int page, int pageSize) {
        juHeJokeService.getJokeContent(page, pageSize)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        juHeJokeView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JokeEntity>() {
                    @Override
                    public void onCompleted() {
                        juHeJokeView.dismissLoading();
                        juHeJokeView.completed();
                        this.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        juHeJokeView.showErrorMessage(e.toString());
                        this.unsubscribe();
                    }

                    @Override
                    public void onNext(JokeEntity jokeEntity) {
                        juHeJokeView.bindJuHeJoke(jokeEntity);
                    }
                });
    }

    @Override
    public void getJokeImgEntity(int page, int pageSize) {
        juHeJokeService.getJokeImg(page, pageSize)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        juHeJokeView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JokeImgEntity>() {
                    @Override
                    public void onCompleted() {
                        juHeJokeView.dismissLoading();
                        juHeJokeView.completed();
                        this.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        juHeJokeView.showErrorMessage(e.toString());
                        this.unsubscribe();
                    }

                    @Override
                    public void onNext(JokeImgEntity jokeImgEntity) {
                        juHeJokeView.bindJHeJokeImg(jokeImgEntity);
                    }
                });
    }
}
