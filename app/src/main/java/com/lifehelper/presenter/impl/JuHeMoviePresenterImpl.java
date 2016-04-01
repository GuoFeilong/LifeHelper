package com.lifehelper.presenter.impl;

import com.lifehelper.entity.MovieEntity;
import com.lifehelper.entity.MovieRecentEntity;
import com.lifehelper.model.MovieModel;
import com.lifehelper.presenter.JuHeMoviePresenter;
import com.lifehelper.tools.Logger;
import com.lifehelper.view.JuHeMovieView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by jsion on 16/3/29.
 */
public class JuHeMoviePresenterImpl implements JuHeMoviePresenter {
    private JuHeMovieView juHeMovieView;
    private MovieModel movieModel;

    public JuHeMoviePresenterImpl(JuHeMovieView juHeMovieView) {
        movieModel = new MovieModel();
        this.juHeMovieView = juHeMovieView;
    }

    @Override
    public void getMovieRecent(String city) {
        movieModel.getJuheMovieService(city).getMovieRecent(city)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        juHeMovieView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieRecentEntity>() {
                    @Override
                    public void onCompleted() {
                        Logger.e("列表onCompleted");
                        juHeMovieView.completed();
                        if (!this.isUnsubscribed()) {
                            unsubscribe();
                            Logger.e("列表解绑");
                        } else {
                            Logger.e("列表mei解绑");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("列表onError:"+e.toString());
                        juHeMovieView.showErrorMessage(e.toString());
                    }

                    @Override
                    public void onNext(MovieRecentEntity movieRecentEntity) {
                        Logger.e("列表onNext");
                        juHeMovieView.bindJHeRecentMovies(movieRecentEntity);
                    }
                });
    }

    @Override
    public void getSearchMovie(String city, String searchKey) {
        movieModel.getJuheMovieService(city).getSearcheMovieRecent(city, searchKey)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        juHeMovieView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieEntity>() {

                    @Override
                    public void onCompleted() {
                        juHeMovieView.completed();
                        if (!this.isUnsubscribed()) {
                            unsubscribe();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        juHeMovieView.showErrorMessage(e.toString());
                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        juHeMovieView.bindJuHeSearchMovie(movieEntity);
                    }
                });
    }
}
