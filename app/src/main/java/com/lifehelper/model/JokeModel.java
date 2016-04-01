package com.lifehelper.model;

import com.lifehelper.service.JuHeJokeService;

/**
 * Created by jsion on 16/4/1.
 */
public class JokeModel {
    private JuHeJokeService juHeJokeService;

    public JuHeJokeService getJuHeJokeService() {
        juHeJokeService = new JuHeJokeService();
        return juHeJokeService;
    }
}
