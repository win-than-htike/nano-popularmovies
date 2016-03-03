package com.plan.yelinaung.popularmovies.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user on 2/23/16.
 */
public class ViewUtils {
  public static Context mContext;

  public ViewUtils(Context context) {
    this.mContext = context;
  }

  public static int dp2px(int dps) {
    final float scale = mContext.getResources().getDisplayMetrics().density;
    return (int) (scale * dps + 0.5f);
  }

  public static int dp2px(int dps, Context context) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (scale * dps + 0.5f);
  }

  public static void setViewsParams(View view, int layoutWidth, int layoutHeight) {
    ViewGroup.LayoutParams params = view.getLayoutParams();
    params.width = layoutWidth;
    params.height = layoutHeight;
    view.setLayoutParams(params);
  }
}
