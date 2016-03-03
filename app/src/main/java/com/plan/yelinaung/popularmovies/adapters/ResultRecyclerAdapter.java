package com.plan.yelinaung.popularmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.plan.yelinaung.popularmovies.R;
import com.plan.yelinaung.popularmovies.models.Review;
import com.plan.yelinaung.popularmovies.view.CircleLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/2/16.
 */
public class ResultRecyclerAdapter
    extends RecyclerView.Adapter<ResultRecyclerAdapter.ItemViewHolder> {

  private List<Review> lists;

  public ResultRecyclerAdapter() {
    lists = new ArrayList<>();
  }

  public ResultRecyclerAdapter(List<Review> lists) {
    this.lists = lists;
    notifyDataSetChanged();
  }

  public void setLists(List<Review> lists) {
    this.lists = lists;
    notifyDataSetChanged();
  }

  @Override public int getItemCount() {
    return lists.size();
  }

  @Override public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ItemViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_review_layout, parent, false));
  }

  @Override public void onBindViewHolder(ItemViewHolder holder, int position) {
    holder.review.setContents(lists.get(position));
  }

  public class ItemViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.review) CircleLayout review;

    public ItemViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
