package com.plan.yelinaung.popularmovies.events;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.plan.yelinaung.popularmovies.R;
import com.plan.yelinaung.popularmovies.utils.ViewUtils;

/**
 * Created by user on 2/22/16.
 */
public class YoutubeThumbnailInitializer implements YouTubeThumbnailView.OnInitializedListener,
    YouTubeThumbnailLoader.OnThumbnailLoadedListener {
  private String video;
  private Context context;

  @Override public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView,
      YouTubeThumbnailLoader.ErrorReason errorReason) {
    youTubeThumbnailView.setImageResource(R.drawable.no_thumbnail);
  }

  @Override public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {

  }

  public YoutubeThumbnailInitializer(String video, Context context) {
    this.video = video;
    this.context = context;
  }

  @Override public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView,
      YouTubeInitializationResult youTubeInitializationResult) {
    Log.d("error", youTubeInitializationResult.toString());
    youTubeThumbnailView.setImageResource(R.drawable.no_thumbnail);
  }

  @Override public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView,
      YouTubeThumbnailLoader youTubeThumbnailLoader) {
    youTubeThumbnailLoader.setVideo(video);
    youTubeThumbnailLoader.setOnThumbnailLoadedListener(this);
    ViewUtils viewUtils = new ViewUtils(context);
    viewUtils.setViewsParams(youTubeThumbnailView, ViewGroup.LayoutParams.MATCH_PARENT,
        ViewUtils.dp2px(200));
  }
}
