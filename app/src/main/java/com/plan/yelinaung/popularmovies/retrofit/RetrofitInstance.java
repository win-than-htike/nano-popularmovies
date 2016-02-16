package com.plan.yelinaung.popularmovies.retrofit;

import com.plan.yelinaung.popularmovies.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 2/14/16.
 */
public class RetrofitInstance {
  public static Retrofit retrofit;

  public static Retrofit getInstance() {
    if (retrofit == null) {
      retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
    }

    return retrofit;
  }
}
