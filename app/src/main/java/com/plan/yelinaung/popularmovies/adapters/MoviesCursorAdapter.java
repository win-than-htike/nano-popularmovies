package com.plan.yelinaung.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.plan.yelinaung.popularmovies.R;
import com.plan.yelinaung.popularmovies.database.MovieDatabaseContract;
import com.plan.yelinaung.popularmovies.events.ClickEvent;
import com.plan.yelinaung.popularmovies.utils.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by user on 2/29/16.
 */
public class MoviesCursorAdapter extends CursorRecyclerViewAdapter<MoviesCursorAdapter.ViewHolder> {
  private Context context;
  private Cursor cursor;
  private ClickEvent clickEvent;

  public MoviesCursorAdapter(Context context, Cursor cursor) {
    super(context, cursor);
    this.context = context;
    this.cursor = cursor;
    notifyDataSetChanged();
  }

  public Cursor getItem(int position) {
    cursor = getCursor();
    cursor.moveToPosition(position);
    return cursor;
  }

  @Override public void onBindViewHolder(final ViewHolder holder, Cursor cursor) {
    Target target = new Target() {
      @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        if (holder.textView.getVisibility() == View.VISIBLE) {
          holder.textView.setVisibility(View.INVISIBLE);
        }
        holder.imageView.setImageBitmap(bitmap);
      }

      @Override public void onBitmapFailed(Drawable errorDrawable) {

      }

      @Override public void onPrepareLoad(Drawable placeHolderDrawable) {
        if (holder.textView.getVisibility() == View.INVISIBLE) {
          holder.textView.setVisibility(View.VISIBLE);
        }
        holder.imageView.setImageDrawable(placeHolderDrawable);
      }
    };
    Picasso.with(context)
        .load(Constants.BASE_IMAGE_URL + cursor.getString(
            cursor.getColumnIndexOrThrow(MovieDatabaseContract.Movies.MOVIE_POSTER_URL)))
        .placeholder(
            context.getResources().getDrawable(R.drawable.background_overlay, context.getTheme()))
        .into(target);
    holder.textView.setText(
        cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseContract.Movies.MOVIE_TITLE)));
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false));
  }

  public void setClickEvent(ClickEvent clickEvent) {
    if (this.clickEvent == null) {
      this.clickEvent = clickEvent;
    }
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.img) ImageView imageView;
    @Bind(R.id.title_text) TextView textView;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
    }

    @Override public void onClick(View view) {
      if (clickEvent != null) {
        clickEvent.onClick(getAdapterPosition(), view);
      }
    }
  }
}