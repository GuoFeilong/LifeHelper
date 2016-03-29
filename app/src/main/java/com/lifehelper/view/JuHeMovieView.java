package com.lifehelper.view;

import com.lifehelper.entity.MovieEntity;

/**
 * Created by jsion on 16/3/29.
 */
public interface JuHeMovieView {
    void showLoading();

    void bindJuHeSearchMovie(MovieEntity movieEntity);

    void bindJHeRecentMovies();

    void showErrorMessage();

    void dismissLoading();
}
