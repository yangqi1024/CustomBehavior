package com.yq.custombehavior;

import android.app.Application;
import android.content.Context;


/**
 * Description ...
 *
 * @author gsz
 * @since V1.0.1
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
    public static Context getmContext() {
        return MyApplication.mContext;
    }
}
