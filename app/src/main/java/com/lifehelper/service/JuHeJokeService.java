package com.lifehelper.service;


import com.lifehelper.app.MyConstance;
import com.lifehelper.entity.JokeEntity;
import com.lifehelper.entity.JokeImgEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jsion on 16/4/1.
 */
public class JuHeJokeService {
    private APIJoke apiJoke;

    public JuHeJokeService() {
        apiJoke = new RetrofitInstance(MyConstance.JUHE_JOKE_BASE_URL)
                .getRetrofit()
                .create(APIJoke.class);
    }

    private interface APIJoke {
        @GET(MyConstance.JUHE_JOKE_JOKE_KEY + "?key=" + MyConstance.JUHE_JOKE_APP_KEY)
        Observable<JokeEntity> getJokeEntity(@Query("page") int page, @Query("pagesize") int pagesize);

        @GET(MyConstance.JUHE_JOKE_JOKE_IMAGE_KEY + "?key=" + MyConstance.JUHE_JOKE_APP_KEY)
        Observable<JokeImgEntity> getJokeImgEntity(@Query("page") int page, @Query("pagesize") int pagesize);
    }


    public Observable<JokeEntity> getJokeContent(int page, int pageSize) {
        return apiJoke.getJokeEntity(page, pageSize);
    }

    public Observable<JokeImgEntity> getJokeImg(int page, int pageSize) {
        return apiJoke.getJokeImgEntity(page, pageSize);
    }
}
