package com.plan.yelinaung.popularmovies;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.plan.yelinaung.popularmovies.models.YoutubeParcel;
import com.plan.yelinaung.popularmovies.utils.Constants;

public class YoutubeActivity extends YouTubeBaseActivity {

  @Bind(R.id.youtube_play) YouTubePlayerView youtubePlay;
  private String key;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_youtube);
    ButterKnife.bind(this);
    YoutubeParcel parcel = getIntent().getParcelableExtra(Constants.PARCEL_MOVIE_NAME);
    key = parcel.getYouTubeKey();

    youtubePlay.initialize(Constants.YOUTUBE_APIKEY, new YouTubePlayer.OnInitializedListener() {
      @Override public void onInitializationSuccess(YouTubePlayer.Provider provider,
          YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(key);
        youTubePlayer.play();
      }

      @Override public void onInitializationFailure(YouTubePlayer.Provider provider,
          YouTubeInitializationResult youTubeInitializationResult) {

      }
    });
  }
}
