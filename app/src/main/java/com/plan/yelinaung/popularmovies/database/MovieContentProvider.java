package com.plan.yelinaung.popularmovies.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by user on 2/28/16.
 */
public class MovieContentProvider extends ContentProvider {

  public static final int MOVIE_LIST = 101;
  public static final int MOVIE_ITEM = 100;
  public static final UriMatcher uriMatcher;
  public static MovieDatabase movieDatabase;

  static {
    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI(MovieDatabaseContract.AUTHORITY, MovieDatabaseContract.Movies.PATH,
        MOVIE_LIST);
    uriMatcher.addURI(MovieDatabaseContract.AUTHORITY, MovieDatabaseContract.Movies.PATH + "/#",
        MOVIE_ITEM);
  }

  @Override public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
    SQLiteDatabase database = movieDatabase.getWritableDatabase();
    int count = 0;
    switch (uriMatcher.match(uri)) {
      case MOVIE_LIST:
        count = database.update(MovieDatabaseContract.Movies.TABLE_NAME, contentValues, s, strings);
        break;
      case MOVIE_ITEM:
        throw new UnsupportedOperationException("No Such URI");
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return count;
  }

  @Nullable @Override
  public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
    Cursor cursor;
    switch (uriMatcher.match(uri)) {
      case MOVIE_ITEM:
        throw new UnsupportedOperationException("No Such Uri");
      case MOVIE_LIST:
        SQLiteDatabase sqLiteDatabase = movieDatabase.getReadableDatabase();
        cursor = sqLiteDatabase.query(MovieDatabaseContract.Movies.TABLE_NAME, strings, s, strings1,
            null, null, s1);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    return null;
  }

  @Override public boolean onCreate() {
    movieDatabase = new MovieDatabase(getContext());
    return true;
  }

  @Nullable @Override public Uri insert(Uri uri, ContentValues contentValues) {
    if (uriMatcher.match(uri) != MOVIE_LIST) {
      throw new UnsupportedOperationException("No Such URI");
    } else {
      ContentValues contentValues1;
      if (contentValues != null) {
        contentValues1 = contentValues;
      } else {
        contentValues1 = new ContentValues();
      }
      SQLiteDatabase database = movieDatabase.getWritableDatabase();
      long count = database.insert(MovieDatabaseContract.Movies.TABLE_NAME, null, contentValues1);
      if (count > 1) {
        Uri uri1 = ContentUris.withAppendedId(MovieDatabaseContract.Movies.MOVIES_URI, count);
        return uri1;
      }
    }
    return null;
  }

  @Nullable @Override public String getType(Uri uri) {
    switch (uriMatcher.match(uri)) {
      case MOVIE_ITEM:
        return MovieDatabaseContract.Movies.MOVIE_ITEM_TYPE;
      case MOVIE_LIST:
        return MovieDatabaseContract.Movies.MOVIE_LIST_TYPE;
      default:
        throw new UnsupportedOperationException("No such URI");
    }
  }

  @Override public int delete(Uri uri, String s, String[] strings) {
    SQLiteDatabase database = movieDatabase.getWritableDatabase();
    switch (uriMatcher.match(uri)) {
      case MOVIE_LIST:
        database.delete(MovieDatabaseContract.Movies.TABLE_NAME, s, strings);
        break;
      case MOVIE_ITEM:
        s = s + MovieDatabaseContract.Movies._ID + " = " + uri.getLastPathSegment();
        break;
    }
    int rows = database.delete(MovieDatabaseContract.Movies.TABLE_NAME, s, strings);
    getContext().getContentResolver().notifyChange(uri, null);
    return rows;
  }

  //  case NOTES:
  //      break;
  //  case NOTES_ID:
  //  where = where + "_id = " + uri.getLastPathSegment();
  //  break;
  //  default:
  //      throw new IllegalArgumentException("Unknown URI " + uri);
  //}
  //  int count = db.delete(NOTES_TABLE_NAME, where, whereArgs);
  //  getContext().getContentResolver().notifyChange(uri, null);
  //    return count;
}
