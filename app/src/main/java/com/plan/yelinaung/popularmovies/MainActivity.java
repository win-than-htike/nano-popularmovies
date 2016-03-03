package com.plan.yelinaung.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.plan.yelinaung.popularmovies.database.MovieDatabaseContract;
import com.plan.yelinaung.popularmovies.fragments.DetailFragment;
import com.plan.yelinaung.popularmovies.fragments.MainFragment;
import com.plan.yelinaung.popularmovies.models.MovieInfo;
import com.plan.yelinaung.popularmovies.utils.Constants;

public class MainActivity extends AppCompatActivity implements MainFragment.OnClick {
  public String DETAIL_TAG = "detail";
  private DetailFragment fragment;

  private boolean multiPane;

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
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

  @Override protected void onResume() {
    super.onResume();
  }

  @Override protected void onPause() {
    if (findViewById(R.id.detail_frame) != null) {
      getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }
    super.onPause();
  }

  @Override public void onClicked(MovieInfo movieInfo) {
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
    if (findViewById(R.id.detail_frame) != null) {
      multiPane = true;
      if (savedInstanceState == null) {
        //getSupportFragmentManager().beginTransaction()
        //    .replace(R.id.detail_frame, new DetailFragment(), DETAIL_TAG)
        //    .commit();
      }
    } else {
      multiPane = false;
      MainFragment mainFragment =
          (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_frame);
    }
  }
}


