package com.lifehelper.view;

import com.lifehelper.entity.MovieEntity;
import com.lifehelper.entity.MovieRecentEntity;

/**
 * Created by jsion on 16/3/29.
 */
public interface JuHeMovieView {
    void showLoading();

    void bindJuHeSearchMovie(MovieEntity movieEntity);

    void bindJHeRecentMovies(MovieRecentEntity movieRecentEntity);

    void showErrorMessage(String error);

    void dismissLoading();

    void completed();
}
