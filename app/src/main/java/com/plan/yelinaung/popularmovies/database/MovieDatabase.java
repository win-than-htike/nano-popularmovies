package com.plan.yelinaung.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by user on 2/28/16.
 */
public class MovieDatabase extends SQLiteOpenHelper {
  public MovieDatabase(Context context) {
    super(context, MovieDatabaseContract.DATABASE_NAME, null,
        MovieDatabaseContract.DATABASE_VERSION);
  }

  @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieDatabaseContract.Movies.TABLE_NAME);
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String databaseQuery = createDatabase(new String[] {
        MovieDatabaseContract.Movies.MOVIE_TITLE, MovieDatabaseContract.Movies.MOVIE_RATING,
        MovieDatabaseContract.Movies.MOVIE_RELEASE_DATE, MovieDatabaseContract.Movies.MOVIE_PLOT,
        MovieDatabaseContract.Movies.MOVIE_POSTER_URL,
    });
    Log.d("Create table", databaseQuery.toString());
    sqLiteDatabase.execSQL(databaseQuery);
  }

  public String createDatabase(String[] columns) {
    StringBuilder builder = new StringBuilder("CREATE TABLE ");
    builder.append(MovieDatabaseContract.Movies.TABLE_NAME);
    builder.append("(");
    builder.append(MovieDatabaseContract.Movies._ID);
    builder.append(" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , ");
    builder.append(MovieDatabaseContract.Movies.MOVIE_ID);
    builder.append(" TEXT  ,");
    builder.append(MovieDatabaseContract.Movies.VOTE_COUNT);
    builder.append(" INTEGER ,");

    for (int i = 0; i < columns.length; i++) {
      builder.append(columns[i]);

      builder.append(", ");
    }

    builder.append(MovieDatabaseContract.Movies.BOOKMARK);
    builder.append(" INTEGER ");
    builder.append(");");
    return builder.toString();
  }
}
