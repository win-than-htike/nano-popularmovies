package com.plan.yelinaung.popularmovies;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import okhttp3.OkHttpClient;

/**
 * Created by user on 2/20/16.
 */
public class MainApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();
  }
}
