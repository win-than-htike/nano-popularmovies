package com.plan.yelinaung.popularmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by user on 3/3/16.
 */
public class InternetUtils {
  public static boolean isOnline(Context context) {
    NetworkInfo networkInfo = null;
    try {
      ConnectivityManager connectivityManager =
          (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      networkInfo = connectivityManager.getActiveNetworkInfo();
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
      return true;
    }
    return false;
  }


}
