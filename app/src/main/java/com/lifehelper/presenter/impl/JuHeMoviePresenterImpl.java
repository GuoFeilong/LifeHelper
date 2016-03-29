package com.lifehelper.presenter.impl;

import com.lifehelper.entity.MovieEntity;
import com.lifehelper.model.MovieModel;
import com.lifehelper.presenter.JuHeMoviePresenter;
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
        movieModel.getJuheMovieService(city).getMovieRecent(city);
    }

    @Override
    public void getSearchMovie(String city, String searchKey) {
        movieModel.getJuheMovieService(city).getSearcheMovieRecent(city,searchKey)
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
                        juHeMovieView.dismissLoading();
                        if (!this.isUnsubscribed()){
                            unsubscribe();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        juHeMovieView.showErrorMessage();
                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        juHeMovieView.bindJuHeSearchMovie(movieEntity);
                    }
                });
    }
}
