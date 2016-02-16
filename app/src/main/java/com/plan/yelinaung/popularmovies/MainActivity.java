package com.plan.yelinaung.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.plan.yelinaung.popularmovies.adapters.PopularMovieAdapter;
import com.plan.yelinaung.popularmovies.customviews.AutofitRecyclerView;
import com.plan.yelinaung.popularmovies.events.ClickEvent;
import com.plan.yelinaung.popularmovies.models.MovieInfo;
import com.plan.yelinaung.popularmovies.models.PopularMovies;
import com.plan.yelinaung.popularmovies.models.Result;
import com.plan.yelinaung.popularmovies.retrofit.PopularService;
import com.plan.yelinaung.popularmovies.retrofit.RetrofitInstance;
import com.plan.yelinaung.popularmovies.utils.Constants;
import com.plan.yelinaung.popularmovies.utils.PrefUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class

MainActivity extends AppCompatActivity implements ClickEvent {
  @Bind(R.id.autofit_recycler) AutofitRecyclerView autofitRecyclerView;
  @Bind(R.id.progress) ProgressBar progressBar;
  private PopularMovieAdapter popularMovieAdapter;

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override public void onClick(int position, View view) {
    Result result = popularMovieAdapter.getItem(position);
    Intent intent = new Intent(this, DetailActivity.class);
    MovieInfo movieInfo = new MovieInfo(result.getOriginalTitle(),
        Constants.BASE_IMAGE_URL + result.getBackdropPath(), result.getOverview(),
        result.getPopularity(), result.getReleaseDate());
    intent.putExtra(Constants.PARCEL_DETAIL_NAME, movieInfo);
    startActivity(intent);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.settings:
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  @Override protected void onResume() {
    super.onResume();
    PrefUtils prefUtils = new PrefUtils(this);
    if (autofitRecyclerView.getVisibility() == View.VISIBLE) {
      autofitRecyclerView.setVisibility(View.INVISIBLE);
    }
    if (progressBar.getVisibility() == View.GONE) {
      progressBar.setVisibility(View.VISIBLE);
    }

    loadData(prefUtils.getSortOrder());
  }

  private void loadData(String value) {

    Retrofit retrofit = RetrofitInstance.getInstance();
    PopularService popularService = retrofit.create(PopularService.class);
    popularMovieAdapter = new PopularMovieAdapter(this);
    popularMovieAdapter.setClickEvent(this);

    Call<PopularMovies> popularMoviesCall =
        popularService.getPopularMovies(value, Constants.API_KEY);
    popularMoviesCall.enqueue(new Callback<PopularMovies>() {
      @Override public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
        PopularMovies popularMovies = response.body();
        popularMovieAdapter.setDatas(popularMovies.getResults());
        progressBar.setVisibility(View.GONE);
        autofitRecyclerView.setVisibility(View.VISIBLE);
        autofitRecyclerView.setAdapter(popularMovieAdapter);
      }

      @Override public void onFailure(Call<PopularMovies> call, Throwable t) {

      }
    });
  }
}


