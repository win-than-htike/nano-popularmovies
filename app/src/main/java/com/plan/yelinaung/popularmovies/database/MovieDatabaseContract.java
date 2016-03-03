package com.plan.yelinaung.popularmovies.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by user on 2/28/16.
 */
public class MovieDatabaseContract {

  public static final String DATABASE_NAME = "themovies";
  public static final int DATABASE_VERSION = 1;
  public static final String AUTHORITY = "com.plan.yelinaung.popularmovies";
  public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

  public static class Movies implements BaseColumns {
    public static final String TABLE_NAME = "movie";
    public static final String MOVIE_ID = "id";
    public static final String MOVIE_TITLE = "title";
    public static final String MOVIE_RATING = "rating";
    public static final String BOOKMARK = "bookmark";
    public static final String MOVIE_RELEASE_DATE = "release_date";
    public static final String MOVIE_PLOT = "plot";
    public static final String MOVIE_POSTER_URL = "poster_url";
    public static final String PATH = "movies";
    public static final String VOTE_COUNT = "vote_count";
    public static final Uri MOVIES_URI = BASE_URI.buildUpon().appendPath(PATH).build();
    public static final String MOVIE_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/movies";
    public static final String MOVIE_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/movie";
  }
}
