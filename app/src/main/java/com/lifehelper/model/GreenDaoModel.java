package com.lifehelper.model;

import android.content.Context;

import com.lifehelper.DaoSession;
import com.lifehelper.app.LifeApplication;

/**
 * Created by jsion on 16/3/22.
 */
public class GreenDaoModel {
    private Context context;

    public GreenDaoModel(Context context) {
        this.context = context;
    }

    public DaoSession getGreenDaoSession() {
        return LifeApplication.getDaoSession(context);
    }
}
