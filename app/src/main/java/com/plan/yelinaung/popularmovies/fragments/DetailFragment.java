package com.plan.yelinaung.popularmovies.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.plan.yelinaung.popularmovies.R;
import com.plan.yelinaung.popularmovies.YoutubeActivity;
import com.plan.yelinaung.popularmovies.adapters.ResultRecyclerAdapter;
import com.plan.yelinaung.popularmovies.database.MovieDatabaseContract;
import com.plan.yelinaung.popularmovies.events.YoutubeThumbnailInitializer;
import com.plan.yelinaung.popularmovies.models.MovieInfo;
import com.plan.yelinaung.popularmovies.models.ReviewList;
import com.plan.yelinaung.popularmovies.models.YoutubeModel;
import com.plan.yelinaung.popularmovies.models.YoutubeParcel;
import com.plan.yelinaung.popularmovies.retrofit.RetrofitInstance;
import com.plan.yelinaung.popularmovies.retrofit.ReviewService;
import com.plan.yelinaung.popularmovies.retrofit.YoutubeService;
import com.plan.yelinaung.popularmovies.utils.Constants;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by user on 3/2/16.
 */
public class DetailFragment extends Fragment {
  private MovieInfo movieInfo;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_detail, container, false);
    ButterKnife.bind(this, view);
    setRetainInstance(true);
    return view;
  }

  @Bind(R.id.detailImage) ImageView imageView;
  @Bind(R.id.original_poster_title) TextView title;
  @Bind(R.id.release_text) TextView releaseDate;
  @Bind(R.id.rating_bar) AppCompatRatingBar ratingBar;
  @Bind(R.id.rating_text) TextView ratingText;
  @Bind(R.id.synopsis) TextView synopsis;
  @Bind(R.id.trailer_container) LinearLayout trailerContainer;
  @Bind(R.id.review_container) RecyclerView recyclerView;
  @Bind(R.id.like) ImageView like;
  @Bind(R.id.rating_card) CardView ratingCard;
  @Bind(R.id.share) Button share;

  @OnClick(R.id.share) void OnShare(View view) {
    Intent sendIntent = new Intent();
    sendIntent.setAction(Intent.ACTION_SEND);
    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey check this out! Its cool!");
    sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + key);
    sendIntent.setType("text/plain");
    startActivity(sendIntent);
  }

  private AlertDialog alertDialog;
  private int id;
  private String key;
  @Bind(R.id.cd) CardView cd;
  private View view;

  public AlertDialog getAlertDialog(String YoutubeKey) {

    AlertDialog.Builder aBuilder = new AlertDialog.Builder(view.getContext());
    aBuilder.setTitle(this.getResources().getString(R.string.trailer_review));
    View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_youtube, null);
    aBuilder.setView(view);
    alertDialog = aBuilder.create();
    YouTubeThumbnailView youTubeThumbnailView =
        (YouTubeThumbnailView) view.findViewById(R.id.youtube_player);
    YoutubeThumbnailInitializer youtubeThumbnailInitializer =
        new YoutubeThumbnailInitializer(YoutubeKey, getActivity());
    youTubeThumbnailView.initialize(Constants.YOUTUBE_APIKEY, youtubeThumbnailInitializer);
    youTubeThumbnailView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), YoutubeActivity.class);
        YoutubeParcel youtubeParcel = new YoutubeParcel(id, key);
        intent.putExtra(Constants.PARCEL_MOVIE_NAME, youtubeParcel);
        startActivity(intent);
      }
    });
    alertDialog.setCanceledOnTouchOutside(true);

    return alertDialog;
  }

  void loadYoutubeInformations() {
    final Retrofit retrofit = RetrofitInstance.getInstance();
    YoutubeService youtubeService = retrofit.create(YoutubeService.class);
    Call<YoutubeModel> youtubeModelCall =
        youtubeService.getYoutubeInformations(id, Constants.MOVIE_DB_API);
    youtubeModelCall.enqueue(new Callback<YoutubeModel>() {
                               @Override public void onResponse(Call<YoutubeModel> call, Response<YoutubeModel> response) {
                                 if (response.body().getResults().size() > 0) {
                                   trailerContainer.setVisibility(View.VISIBLE);
                                   List<YoutubeModel.YoutubeResult> results = response.body().getResults();
                                   key = response.body().getResults().get(0).getKey();
                                   share.setVisibility(View.VISIBLE);
                                   for (final YoutubeModel.YoutubeResult youtubeResult : results) {
                                     // new Handler().post(new Runnable() {
                                     // @Override public void run() {
                                     view = LayoutInflater.from(view.getContext())
                                         .inflate(R.layout.item_trailer, trailerContainer, false);
                                     TextView textView = (TextView) view.findViewById(R.id.movie_trailer_item);
                                     textView.setText(youtubeResult.getName());

                                     view.setOnClickListener(new View.OnClickListener() {
                                       @Override public void onClick(View view) {
                                         getAlertDialog(youtubeResult.getKey()).show();
                                       }
                                     });
                                     trailerContainer.addView(view);
                                     //   }
                                     //});
                                   }
                                 } else

                                 {
                                   trailerContainer.setVisibility(View.GONE);
                                 }
                               }

                               @Override public void onFailure(Call<YoutubeModel> call, Throwable t) {
                                 Toast.makeText(view.getContext(), "Error Occured", Toast.LENGTH_LONG).show();
                               }
                             }

    );
    ReviewService reviewService = retrofit.create(ReviewService.class);
    Call<ReviewList> reviewListCall = reviewService.getReviews(id + "", Constants.MOVIE_DB_API);
    reviewListCall.enqueue(new Callback<ReviewList>()

                           {
                             @Override public void onResponse(Call<ReviewList> call, final Response<ReviewList> response) {
                               if (response.body().getResults().size() > 0) {
                                 recyclerView.setVisibility(View.VISIBLE);

                                 new Handler().post(new Runnable() {
                                   @Override public void run() {
                                     ResultRecyclerAdapter resultRecyclerAdapter = new ResultRecyclerAdapter();
                                     recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                                     recyclerView.setHasFixedSize(true);
                                     recyclerView.setAdapter(resultRecyclerAdapter);
                                     resultRecyclerAdapter.setLists(response.body().getResults());
                                   }
                                 });
                               } else {
                                 recyclerView.setVisibility(View.GONE);
                               }
                             }

                             @Override public void onFailure(Call<ReviewList> call, Throwable t) {

                             }
                           }

    );
  }

  @Override public void onResume() {
    super.onResume();
    Bundle args = getArguments();
    if (args != null) {
      movieInfo = args.getParcelable(Constants.PARCEL_DETAIL_NAME);

      Picasso.with(view.getContext()).load(movieInfo.getImageUrl()).fit().into(imageView);
      id = movieInfo.getId();
      recyclerView.setVisibility(View.GONE);
      title.setText(movieInfo.getTitle());
      releaseDate.setText(movieInfo.getReleaseDate());
      float rating = (float) (movieInfo.getRating() / 10);
      ratingBar.setRating(rating);
      ratingText.setText(getString(R.string.ratingformat, rating));
      synopsis.setText(movieInfo.getSynopsis());
      like.setColorFilter(Color.GRAY);
      share.setVisibility(View.INVISIBLE);
      Cursor cursor = view.getContext()
          .getContentResolver()
          .query(MovieDatabaseContract.Movies.MOVIES_URI,
              new String[] { MovieDatabaseContract.Movies.BOOKMARK },
              MovieDatabaseContract.Movies.MOVIE_ID + " = " + id, null, null);
      cursor.moveToPosition(0);
      switch (cursor.getInt(cursor.getColumnIndexOrThrow(MovieDatabaseContract.Movies.BOOKMARK))) {
        case 0:
          like.setColorFilter(Color.GRAY);
          break;
        case 1:
          like.setColorFilter(Color.RED);
          break;

        default:
          like.setColorFilter(Color.GRAY);
          break;
      }
      cursor.close();
      like.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          Cursor cursor = view.getContext()
              .getContentResolver()
              .query(MovieDatabaseContract.Movies.MOVIES_URI,
                  new String[] { MovieDatabaseContract.Movies.BOOKMARK },
                  MovieDatabaseContract.Movies.MOVIE_ID + " = " + id, null, null);
          cursor.moveToPosition(0);
          ContentValues contentValues = new ContentValues();
          switch (cursor.getInt(
              cursor.getColumnIndexOrThrow(MovieDatabaseContract.Movies.BOOKMARK))) {
            case 0:
              contentValues.put(MovieDatabaseContract.Movies.BOOKMARK, 1);

              int d = view.getContext()
                  .getContentResolver()
                  .update(MovieDatabaseContract.Movies.MOVIES_URI, contentValues,
                      MovieDatabaseContract.Movies.MOVIE_ID + " = " + id, null);
              like.setColorFilter(Color.RED);
              break;
            case 1:
              contentValues = new ContentValues();
              contentValues.put(MovieDatabaseContract.Movies.BOOKMARK, 0);
              like.setColorFilter(Color.GRAY);
              view.getContext()
                  .getContentResolver()
                  .update(MovieDatabaseContract.Movies.MOVIES_URI, contentValues,
                      MovieDatabaseContract.Movies.MOVIE_ID + " = " + id, null);
              break;

            default:
              like.setColorFilter(Color.GRAY);
              break;
          }
          cursor.close();
        }
      });
      loadYoutubeInformations();
    }
  }
}

