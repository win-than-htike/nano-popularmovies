package com.plan.yelinaung.popularmovies.retrofit;

import com.plan.yelinaung.popularmovies.models.PopularMovies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by user on 2/14/16.
 */
public interface PopularService {

  @GET("discover/movie") Call<PopularMovies> getPopularMovies(@Query("sort_by") String sortOrder,
      @Query("api_key") String api_key, @Query("page") int page);
}
