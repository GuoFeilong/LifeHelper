package com.lifehelper.view;

import com.lifehelper.entity.JokeEntity;
import com.lifehelper.entity.JokeImgEntity;

/**
 * Created by jsion on 16/3/29.
 */
public interface JuHeJokeView {
    void showLoading();

    void bindJuHeJoke(JokeEntity jokeEntity);

    void bindJHeJokeImg(JokeImgEntity jokeImgEntity);

    void showErrorMessage(String error);

    void dismissLoading();

    void completed();
}
