package com.plan.yelinaung.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 3/2/16.
 */
public class YoutubeParcel implements Parcelable {
  public String YouTubeKey;
  public int id;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(id);
    parcel.writeString(YouTubeKey);
  }

  public YoutubeParcel() {

  }

  public YoutubeParcel(int id, String youTubeKey) {
    this.id = id;
    YouTubeKey = youTubeKey;
  }

  public void setYouTubeKey(String youTubeKey) {
    YouTubeKey = youTubeKey;
  }

  public void setId(int id) {

    this.id = id;
  }

  public String getYouTubeKey() {

    return YouTubeKey;
  }

  public int getId() {

    return id;
  }

  public static final Parcelable.Creator CREATOR = new Creator() {
    @Override public YoutubeParcel createFromParcel(Parcel parcel) {
      return new YoutubeParcel(parcel);
    }

    @Override public YoutubeParcel[] newArray(int i) {
      return new YoutubeParcel[i];
    }
  };

  public YoutubeParcel(Parcel in) {
    id = in.readInt();
    YouTubeKey = in.readString();
  }
}
