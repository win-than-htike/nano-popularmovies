package com.plan.yelinaung.popularmovies.utils;

import android.content.Context;
import android.preference.PreferenceManager;
import com.plan.yelinaung.popularmovies.R;

/**
 * Created by user on 2/16/16.
 */
public class PrefUtils {
  private Context context;

  public PrefUtils() {
  }

  public PrefUtils(Context context) {
    this.context = context;
  }

  public String getSortOrder() {
    return (PreferenceManager.getDefaultSharedPreferences(context)).getString(
        context.getString(R.string.key_sort_pref),
        context.getString(R.string.popularity_popular_desc));
  }
}
