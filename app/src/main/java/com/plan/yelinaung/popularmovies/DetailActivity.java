package com.plan.yelinaung.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.plan.yelinaung.popularmovies.models.MovieInfo;
import com.plan.yelinaung.popularmovies.utils.Constants;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
  @Bind(R.id.detailImage) ImageView imageView;
  @Bind(R.id.original_poster_title) TextView title;
  @Bind(R.id.release_text) TextView releaseDate;
  @Bind(R.id.rating_bar) AppCompatRatingBar ratingBar;
  @Bind(R.id.rating_text) TextView ratingText;
  @Bind(R.id.synopsis) TextView synopsis;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    ButterKnife.bind(this);
    MovieInfo movieInfo = getIntent().getParcelableExtra(Constants.PARCEL_DETAIL_NAME);
    Picasso.with(this).load(movieInfo.getImageUrl()).fit().into(imageView);
    title.setText(movieInfo.getTitle());
    releaseDate.setText(movieInfo.getReleaseDate());
    float rating = (float) (movieInfo.getRating() / 10);
    ratingBar.setRating(rating);
    ratingText.setText(getString(R.string.ratingformat, rating));
    synopsis.setText(movieInfo.getSynopsis());
  }
}
