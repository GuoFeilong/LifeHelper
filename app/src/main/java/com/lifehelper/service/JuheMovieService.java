package com.lifehelper.service;

import com.lifehelper.app.MyConstance;
import com.lifehelper.entity.MovieEntity;

import javax.security.auth.callback.Callback;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jsion on 16/3/29.
 */
public class JuheMovieService {
    private APIMovieService apiMovieService;
    private String city;
    private String movieName;
    private String appKey;


    public static class Bulider {
        private String city;
        private String movieName;
        private String appKey;

        public Bulider(String appKey) {
            this.appKey = appKey;
        }

        public Bulider city(String city) {
            this.city = city;
            return this;
        }

        public Bulider movieName(String movieName) {
            this.movieName = movieName;
            return this;
        }

        public JuheMovieService build() {
            return new JuheMovieService(this);
        }
    }


    private JuheMovieService(Bulider bulider) {
        this.city = bulider.city;
        this.appKey = bulider.appKey;
        this.movieName = bulider.movieName;

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyConstance.JUHE_MOVIE_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        apiMovieService = retrofit.create(APIMovieService.class);
    }

    private interface APIMovieService {
        @POST(MyConstance.JUHE_MOVIE_PMOVIE_KEY + "?key=" + MyConstance.JUHE_APP_KEY + "&dtype=")
        Callback getMovieRecent(@Query("city") String city);

        @POST(MyConstance.JUHE_MOVIE_VIDEO_KEY + "?key=" + MyConstance.JUHE_APP_KEY + "&dtype=")
        Observable<MovieEntity> getSearcheMovieRecent(@Query("city") String city, @Query("q") String q);
    }


    public Callback getMovieRecent(String city) {
        return apiMovieService.getMovieRecent(city);
    }

    public Observable<MovieEntity>  getSearcheMovieRecent(String city, String q) {
        return apiMovieService.getSearcheMovieRecent(city, q);
    }
}
