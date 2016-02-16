package com.plan.yelinaung.popularmovies.adapters;

import android.content.Context;
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
import com.plan.yelinaung.popularmovies.events.ClickEvent;
import com.plan.yelinaung.popularmovies.models.Result;
import com.plan.yelinaung.popularmovies.utils.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.util.List;

/**
 * Created by user on 2/14/16.
 */
public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.ItemViewHolder> {
  private ClickEvent clickEvent;
  private List<Result> popularMoviesResult;
  private Context context;

  public PopularMovieAdapter(Context context) {
    this.context = context;
  }

  public void setClickEvent(ClickEvent clickEvent) {
    this.clickEvent = clickEvent;
  }

  public void setDatas(List<Result> popularMovies) {
    this.popularMoviesResult = popularMovies;
    notifyDataSetChanged();
  }

  public Result getItem(int position) {
    return popularMoviesResult.get(position);
  }

  @Override

  public int getItemCount() {
    return popularMoviesResult.size();
  }

  @Override
  public PopularMovieAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ItemViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false));
  }

  @Override
  public void onBindViewHolder(final PopularMovieAdapter.ItemViewHolder holder, int position) {
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
        .load(Constants.BASE_IMAGE_URL + popularMoviesResult.get(position).getPosterPath())
        .placeholder(
            context.getResources().getDrawable(R.drawable.background_overlay, context.getTheme()))
        .into(target);
    holder.textView.setText(popularMoviesResult.get(position).getOriginalTitle());
  }

  public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.img) ImageView imageView;
    @Bind(R.id.title_text) TextView textView;

    public ItemViewHolder(View itemView) {
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
