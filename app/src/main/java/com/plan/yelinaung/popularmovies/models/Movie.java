package com.plan.yelinaung.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2/23/16.
 */
public class Movie implements Parcelable {
  private String movieName;

  public Movie(String movieName) {
    this.movieName = movieName;
  }

  public String getMovieName() {
    return movieName;
  }

  public void setMovieName(String movieName) {
    this.movieName = movieName;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(movieName);
  }

  public static final Parcelable.Creator CREATOR = new Creator() {
    @Override public Movie createFromParcel(Parcel parcel) {
      return new Movie(parcel);
    }

    @Override public Object[] newArray(int i) {
      return new Movie[i];
    }
  };

  public Movie(Parcel in) {
    movieName = in.readString();
  }
}
