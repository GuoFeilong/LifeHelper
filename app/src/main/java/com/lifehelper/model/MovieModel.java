package com.lifehelper.model;

import com.lifehelper.service.JuheMovieService;

/**
 * Created by jsion on 16/3/29.
 */
public class MovieModel {
    private JuheMovieService juheMovieService;

    public JuheMovieService getJuheMovieService(String city) {
        juheMovieService = new JuheMovieService.Bulider(city).build();
        return juheMovieService;
    }
}
