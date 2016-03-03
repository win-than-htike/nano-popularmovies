package com.plan.yelinaung.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.plan.yelinaung.popularmovies.fragments.DetailFragment;
import com.plan.yelinaung.popularmovies.utils.Constants;

public class DetailActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    if (savedInstanceState == null) {
      Bundle args = new Bundle();
      args.putParcelable(Constants.PARCEL_DETAIL_NAME,
          getIntent().getParcelableExtra(Constants.PARCEL_DETAIL_NAME));
      DetailFragment detailFragment = new DetailFragment();
      detailFragment.setArguments(args);
      getSupportFragmentManager().beginTransaction()
          .add(R.id.detail_frame, detailFragment)
          .commit();
    }
  }
}
