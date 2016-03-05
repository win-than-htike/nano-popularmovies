package com.plan.yelinaung.popularmovies;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.plan.yelinaung.popularmovies.database.MovieDatabaseContract;
import com.plan.yelinaung.popularmovies.fragments.DetailFragment;
import com.plan.yelinaung.popularmovies.fragments.MainFragment;
import com.plan.yelinaung.popularmovies.models.MovieInfo;
import com.plan.yelinaung.popularmovies.utils.Constants;
import com.plan.yelinaung.popularmovies.utils.InternetUtils;

public class MainActivity extends AppCompatActivity implements MainFragment.OnClick {
  public String DETAIL_TAG = "detail";
  private DetailFragment fragment;
  private MovieInfo movieInfo = null;
  private boolean isClicked = false;

  private boolean multiPane;

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    if (savedInstanceState != null) {
      movieInfo = savedInstanceState.getParcelable(Constants.SAVED_MOVIE);
      onClicked(movieInfo);
    }
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    if (movieInfo != null && isClicked) {
      outState.putParcelable(Constants.SAVED_MOVIE, movieInfo);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.settings:
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        int rows = getContentResolver().delete(MovieDatabaseContract.Movies.MOVIES_URI,
            MovieDatabaseContract.Movies.BOOKMARK + " = 0 ", null);
        Log.d("Rows Deleted", rows + "  ss");
        startActivity(intent);
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onPause() {
    if (getSupportFragmentManager().findFragmentByTag(DETAIL_TAG) != null) {
      getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }
    super.onPause();
  }

  public MovieInfo getMovieInfo() {
    return movieInfo;
  }

  @Override public void onClicked(MovieInfo movieInfo) {

    this.movieInfo = movieInfo;
    isClicked = true;
    if (multiPane) {
      Bundle args = new Bundle();
      args.putParcelable(Constants.PARCEL_DETAIL_NAME, movieInfo);
      fragment = new DetailFragment();
      fragment.setArguments(args);
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.detail_frame, fragment, DETAIL_TAG)
          .commit();
    } else {
      Intent intent = new Intent(this, DetailActivity.class);
      intent.putExtra(Constants.PARCEL_DETAIL_NAME, movieInfo);
      startActivity(intent);
    }
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (!InternetUtils.isOnline(this)) {
      AlertDialog.Builder abuilder = new AlertDialog.Builder(this);
      abuilder.setTitle("No internet Connection");
      abuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
        @Override public void onClick(DialogInterface dialogInterface, int i) {

        }
      });
      abuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override public void onClick(DialogInterface dialogInterface, int i) {
          Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
          startActivity(intent);
        }
      });
      AlertDialog alertDialog = abuilder.create();
      alertDialog.setMessage("No internet Connection! would u like to review favourites??");
      alertDialog.show();
    }
    if (findViewById(R.id.detail_frame) != null) {
      multiPane = true;
      if (savedInstanceState == null) {
        if (getSupportFragmentManager().findFragmentByTag(DETAIL_TAG) == null) {

        }
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.detail_frame, new DetailFragment(), DETAIL_TAG)
            .commit();
      }
    } else {
      multiPane = false;
      MainFragment mainFragment =
          (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_frame);
    }
  }
}


