package com.lifehelper.app;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.lifehelper.DaoMaster;
import com.lifehelper.DaoSession;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by jsion on 16/3/8.
 */
public class LifeApplication extends Application {
    private RefWatcher refWatcher;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public static RefWatcher getRefWatcher(Context context) {
        LifeApplication application = (LifeApplication) context.getApplicationContext();
        return application.refWatcher;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
//        refWatcher = LeakCanary.install(this);
    }

    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, GreenDaoMyConstant.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
