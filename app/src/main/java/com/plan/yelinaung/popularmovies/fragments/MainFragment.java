package com.plan.yelinaung.popularmovies.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.plan.yelinaung.popularmovies.R;
import com.plan.yelinaung.popularmovies.SettingsActivity;
import com.plan.yelinaung.popularmovies.adapters.MoviesCursorAdapter;
import com.plan.yelinaung.popularmovies.adapters.PopularMovieAdapter;
import com.plan.yelinaung.popularmovies.customviews.AutofitRecyclerView;
import com.plan.yelinaung.popularmovies.database.MovieDatabaseContract;
import com.plan.yelinaung.popularmovies.events.ClickEvent;
import com.plan.yelinaung.popularmovies.listeners.RecyclerViewScroll;
import com.plan.yelinaung.popularmovies.models.MovieInfo;
import com.plan.yelinaung.popularmovies.models.PopularMovies;
import com.plan.yelinaung.popularmovies.models.Result;
import com.plan.yelinaung.popularmovies.retrofit.PopularService;
import com.plan.yelinaung.popularmovies.retrofit.RetrofitInstance;
import com.plan.yelinaung.popularmovies.utils.Constants;
import com.plan.yelinaung.popularmovies.utils.InternetUtils;
import com.plan.yelinaung.popularmovies.utils.PrefUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by user on 3/2/16.
 */
public class MainFragment extends Fragment
    implements LoaderManager.LoaderCallbacks<Cursor>, ClickEvent {
  private LoaderManager.LoaderCallbacks<Cursor> mLoader;
  private View view;
  int clicked_position;

  public interface OnClick {
    void onClicked(MovieInfo movieInfo);
  }

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_main, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  public static final int LOADER_ID = 0x01;
  private MoviesCursorAdapter adapter;
  private PopularService popularService;
  private RecyclerViewScroll recyclerViewScroll;
  private PrefUtils prefUtils;
  private boolean favourites;

  @Override public Loader onCreateLoader(int i, Bundle bundle) {

    switch (i) {

      case LOADER_ID:
        if (favourites) {
          prefUtils = new PrefUtils(view.getContext());
          return new CursorLoader(view.getContext(), MovieDatabaseContract.Movies.MOVIES_URI,
              new String[] {
                  MovieDatabaseContract.Movies._ID, MovieDatabaseContract.Movies.MOVIE_ID,
                  MovieDatabaseContract.Movies.MOVIE_TITLE, MovieDatabaseContract.Movies.MOVIE_PLOT,
                  MovieDatabaseContract.Movies.MOVIE_RELEASE_DATE,
                  MovieDatabaseContract.Movies.MOVIE_RATING,
                  MovieDatabaseContract.Movies.MOVIE_POSTER_URL,
                  MovieDatabaseContract.Movies.VOTE_COUNT, MovieDatabaseContract.Movies.BOOKMARK
              }, MovieDatabaseContract.Movies.BOOKMARK + " == 1 ", null, null);
        } else {
          prefUtils = new PrefUtils(view.getContext());
          if (prefUtils.getSortOrder()
              .equalsIgnoreCase(getResources().getString(R.string.list_pref_popular_desc))) {

            return new CursorLoader(view.getContext(), MovieDatabaseContract.Movies.MOVIES_URI,
                new String[] {
                    MovieDatabaseContract.Movies._ID, MovieDatabaseContract.Movies.MOVIE_ID,
                    MovieDatabaseContract.Movies.MOVIE_TITLE,
                    MovieDatabaseContract.Movies.MOVIE_PLOT,
                    MovieDatabaseContract.Movies.MOVIE_RELEASE_DATE,
                    MovieDatabaseContract.Movies.MOVIE_RATING,
                    MovieDatabaseContract.Movies.MOVIE_POSTER_URL,
                    MovieDatabaseContract.Movies.VOTE_COUNT, MovieDatabaseContract.Movies.BOOKMARK
                }, null, null, MovieDatabaseContract.Movies.MOVIE_ID + " DESC ");
          } else if (prefUtils.getSortOrder()
              .equalsIgnoreCase(getResources().getString(R.string.list_pref_vote_desc))) {

            return new CursorLoader(view.getContext(), MovieDatabaseContract.Movies.MOVIES_URI,
                new String[] {
                    MovieDatabaseContract.Movies._ID, MovieDatabaseContract.Movies.MOVIE_ID,
                    MovieDatabaseContract.Movies.MOVIE_TITLE,
                    MovieDatabaseContract.Movies.MOVIE_PLOT,
                    MovieDatabaseContract.Movies.MOVIE_RELEASE_DATE,
                    MovieDatabaseContract.Movies.MOVIE_RATING,
                    MovieDatabaseContract.Movies.MOVIE_POSTER_URL,
                    MovieDatabaseContract.Movies.VOTE_COUNT, MovieDatabaseContract.Movies.BOOKMARK
                }, null, null, MovieDatabaseContract.Movies.VOTE_COUNT + " DESC ");
          } else {
            return new CursorLoader(view.getContext(), MovieDatabaseContract.Movies.MOVIES_URI,
                new String[] {
                    MovieDatabaseContract.Movies._ID, MovieDatabaseContract.Movies.MOVIE_ID,
                    MovieDatabaseContract.Movies.MOVIE_TITLE,
                    MovieDatabaseContract.Movies.MOVIE_PLOT,
                    MovieDatabaseContract.Movies.MOVIE_RELEASE_DATE,
                    MovieDatabaseContract.Movies.MOVIE_RATING,
                    MovieDatabaseContract.Movies.MOVIE_POSTER_URL,
                    MovieDatabaseContract.Movies.VOTE_COUNT, MovieDatabaseContract.Movies.BOOKMARK
                }, null, null, MovieDatabaseContract.Movies.BOOKMARK + " DESC ");
          }
        }
    }
    return null;
  }

  @Override public void onLoaderReset(Loader<Cursor> loader) {
    adapter.swapCursor(null);
  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    adapter.swapCursor(data);
    adapter.setClickEvent(this);
  }

  @Bind(R.id.autofit_recycler) AutofitRecyclerView autofitRecyclerView;
  @Bind(R.id.progress) ProgressBar progressBar;
  private PopularMovieAdapter popularMovieAdapter;

  @Override public void onClick(int position, View view) {
    clicked_position = position;
    Cursor cursor = adapter.getItem(position);

    MovieInfo movieInfo = new MovieInfo(
        cursor.getInt(cursor.getColumnIndexOrThrow(MovieDatabaseContract.Movies.MOVIE_ID)),
        cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseContract.Movies.MOVIE_TITLE)),
        Constants.BASE_IMAGE_URL + cursor.getString(
            cursor.getColumnIndexOrThrow(MovieDatabaseContract.Movies.MOVIE_POSTER_URL)),
        cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseContract.Movies.MOVIE_PLOT)),
        Double.parseDouble(cursor.getString(
            cursor.getColumnIndexOrThrow(MovieDatabaseContract.Movies.MOVIE_RATING))),
        cursor.getString(
            cursor.getColumnIndexOrThrow(MovieDatabaseContract.Movies.MOVIE_RELEASE_DATE)));
    ((OnClick) (getActivity())).onClicked(movieInfo);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.settings:
        Intent intent = new Intent(getContext(), SettingsActivity.class);
        startActivity(intent);
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onResume() {
    super.onResume();
    mLoader = this;
    prefUtils = new PrefUtils(getContext());
    if (autofitRecyclerView.getVisibility() == View.VISIBLE) {
      autofitRecyclerView.setVisibility(View.INVISIBLE);
    }
    if (progressBar.getVisibility() == View.GONE) {
      progressBar.setVisibility(View.VISIBLE);
    }

    Retrofit retrofit = RetrofitInstance.getInstance();
    popularService = retrofit.create(PopularService.class);

    adapter = new MoviesCursorAdapter(view.getContext(), null);
    autofitRecyclerView.setAdapter(adapter);
    if (InternetUtils.isOnline(view.getContext())) {
      if (prefUtils.getSortOrder() == getResources().getString(R.string.favoruites)) {
        favourites = true;
        progressBar.setVisibility(View.GONE);
        autofitRecyclerView.setVisibility(View.VISIBLE);
        getLoaderManager().restartLoader(LOADER_ID, null, MainFragment.this);
        getLoaderManager().initLoader(LOADER_ID, null, MainFragment.this);
      } else {
        favourites = false;
        loadData(prefUtils.getSortOrder(), 1);
        recyclerViewScroll = new RecyclerViewScroll() {
          @Override public void onLoadMore(int current_page) {
            loadData(prefUtils.getSortOrder(), current_page);
          }
        };
        autofitRecyclerView.addOnScrollListener(recyclerViewScroll);
      }
    } else {
      getLoaderManager().restartLoader(LOADER_ID, null, MainFragment.this);
      getLoaderManager().initLoader(LOADER_ID, null, MainFragment.this);
      progressBar.setVisibility(View.GONE);
      autofitRecyclerView.setVisibility(View.VISIBLE);
    }
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    outState.putInt(Constants.ISSAVED_DATAS, clicked_position);
    super.onSaveInstanceState(outState);
  }

  @Override public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);
    if (savedInstanceState != null) {
      getLoaderManager().restartLoader(LOADER_ID, null, MainFragment.this);
      getLoaderManager().initLoader(LOADER_ID, null, MainFragment.this);
    }
  }

  private void loadData(String value, int page) {
    Toast.makeText(view.getContext(), "Loading more", Toast.LENGTH_SHORT).show();
    Call<PopularMovies> popularMoviesCall =
        popularService.getPopularMovies(value, Constants.MOVIE_DB_API, page);
    popularMoviesCall.enqueue(new Callback<PopularMovies>() {
      @Override public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
        if (response.body().getResults().size() > 0) {
          for (Result result : response.body().getResults()) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieDatabaseContract.Movies.MOVIE_ID, result.getId());
            contentValues.put(MovieDatabaseContract.Movies.MOVIE_TITLE, result.getTitle());
            contentValues.put(MovieDatabaseContract.Movies.MOVIE_PLOT, result.getOverview());
            contentValues.put(MovieDatabaseContract.Movies.MOVIE_RATING,
                Double.toString(result.getPopularity()));
            contentValues.put(MovieDatabaseContract.Movies.VOTE_COUNT, result.getVoteCount());
            contentValues.put(MovieDatabaseContract.Movies.MOVIE_POSTER_URL,
                result.getPosterPath());
            contentValues.put(MovieDatabaseContract.Movies.MOVIE_RELEASE_DATE,
                result.getReleaseDate());
            if (view.getContext()
                .getContentResolver()
                .update(MovieDatabaseContract.Movies.MOVIES_URI, contentValues,
                    MovieDatabaseContract.Movies.MOVIE_ID + " = " + result.getId(), null) <= 0) {
              contentValues.put(MovieDatabaseContract.Movies.BOOKMARK, 0);
              view.getContext()
                  .getContentResolver()
                  .insert(MovieDatabaseContract.Movies.MOVIES_URI, contentValues);
            }
          }
          progressBar.setVisibility(View.GONE);
          autofitRecyclerView.setVisibility(View.VISIBLE);

          getLoaderManager().restartLoader(LOADER_ID, null, MainFragment.this);
          getLoaderManager().initLoader(LOADER_ID, null, MainFragment.this);
        } else {
          recyclerViewScroll.end();
        }
      }

      @Override public void onFailure(Call<PopularMovies> call, Throwable t) {

      }
    });
  }
}

