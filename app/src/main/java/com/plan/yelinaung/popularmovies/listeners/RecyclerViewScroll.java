package com.plan.yelinaung.popularmovies.listeners;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by user on 3/2/16.
 */
public abstract class RecyclerViewScroll extends RecyclerView.OnScrollListener {

  private boolean loading = true;
  int pastVisiblesItems, visibleItemCount, totalItemCount;
  int current_page = 2;

  public void end() {
    loading = false;
  }

  @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);
    if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
      GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

      if (dy > 0) //check for scroll down
      {

        visibleItemCount = layoutManager.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
        if (loading) {
          if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
            current_page++;
            onLoadMore(current_page);

            //Do pagination.. i.e. fetch new data
          }
        }
      }
    } else {
      LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

      if (dy > 0) //check for scroll down
      {
        visibleItemCount = layoutManager.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
        if (loading) {
          if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
            current_page++;
            onLoadMore(current_page);

            //Do pagination.. i.e. fetch new data
          }
        }
      }
    }
  }

  public abstract void onLoadMore(int current_page);
}
