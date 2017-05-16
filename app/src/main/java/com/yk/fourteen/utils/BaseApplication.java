package com.yk.fourteen.utils;

import android.app.Application;

import com.yk.fourteen.common.Common;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import cn.bmob.v3.Bmob;
import cn.sharesdk.framework.ShareSDK;
import okhttp3.OkHttpClient;

/**
 * Created by YAY on 2017/5/15.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
       ShareSDK.initSDK(this);
        //Bmob ID
        Bmob.initialize(this, Common.BMOB_ID);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
