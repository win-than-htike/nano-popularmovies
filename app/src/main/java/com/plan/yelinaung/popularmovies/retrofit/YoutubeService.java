package com.plan.yelinaung.popularmovies.retrofit;

import com.plan.yelinaung.popularmovies.models.YoutubeModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by user on 3/2/16.
 */
public interface YoutubeService {
  //api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=cae383be2635217d38b151b9c844c246
  //http://api.themoviedb.org/3/movie/500/videos?api_key=cae383be2635217d38b151b9c844c246
  @GET("movie/{id}/videos?") Call<YoutubeModel> getYoutubeInformations(@Path("id") int id,
      @Query("api_key") String api_key);
}
