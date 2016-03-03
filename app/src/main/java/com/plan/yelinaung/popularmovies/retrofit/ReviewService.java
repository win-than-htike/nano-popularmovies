package com.plan.yelinaung.popularmovies.retrofit;

import com.plan.yelinaung.popularmovies.models.ReviewList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by user on 3/2/16.
 */

public interface ReviewService {
  @GET("movie/{id}/reviews?") Call<ReviewList> getReviews(@Path("id") String id,
      @Query("api_key") String apiKey);
}
