package com.plan.yelinaung.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2/16/16.
 */
public class MovieInfo implements Parcelable {

  private String title;
  private String imageUrl;
  private String synopsis;
  private double rating;
  private String releaseDate;
  private int id;

  public MovieInfo(int id, String title, String imageUrl, String synopsis, double rating,
      String releaseDate) {
    this.id = id;
    this.title = title;
    this.imageUrl = imageUrl;
    this.synopsis = synopsis;
    this.rating = rating;
    this.releaseDate = releaseDate;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getSynopsis() {
    return synopsis;
  }

  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public MovieInfo() {

  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(id);
    parcel.writeString(title);
    parcel.writeString(imageUrl);
    parcel.writeString(synopsis);
    parcel.writeDouble(rating);
    parcel.writeString(releaseDate);
  }

  public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
    public MovieInfo createFromParcel(Parcel in) {
      return new MovieInfo(in);
    }

    public MovieInfo[] newArray(int size) {
      return new MovieInfo[size];
    }
  };

  public MovieInfo(Parcel in) {
    id = in.readInt();
    title = in.readString();
    imageUrl = in.readString();
    synopsis = in.readString();
    rating = in.readDouble();
    releaseDate = in.readString();
  }
}
