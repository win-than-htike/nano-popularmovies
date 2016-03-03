package com.plan.yelinaung.popularmovies.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.plan.yelinaung.popularmovies.R;
import com.plan.yelinaung.popularmovies.models.Review;

/**
 * Created by user on 2/25/16.
 */
public class CircleLayout extends RelativeLayout {
  @Bind(R.id.circle) CircleView cv;
  @Bind(R.id.textOne) TextView textOne;
  @Bind(R.id.content_title) TextView contentTitle;

  public CircleLayout(Context context) {
    super(context);
    init(context);
  }

  public CircleLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleLayout, 0, 0);
    typedArray.recycle();
    setSaveEnabled(true);
  }

  public CircleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  public void init(Context context) {
    LayoutInflater inflater = LayoutInflater.from(context);
    inflater.inflate(R.layout.custom_layout, this);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

  public void setContents(Review review) {
    textOne.setText(review.getAuthor());
    contentTitle.setText(review.getContent());
    cv.setStartText(review.getAuthor());
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }
}
