package com.plan.yelinaung.popularmovies.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import com.plan.yelinaung.popularmovies.R;

/**
 * Created by user on 2/25/16.
 */
public class CircleView extends View {
  private boolean showText;
  private int textColor;
  private int circleColor;
  private String startText;
  private Paint paint, textPaint;
  private int circle_radius;

  public CircleView(Context context) {
    super(context);
  }

  @Override protected Parcelable onSaveInstanceState() {
    Parcelable superState = super.onSaveInstanceState();
    CircleState circleState = new CircleState(superState);
    circleState.color = this.circleColor;
    return super.onSaveInstanceState();
  }

  @Override protected void onRestoreInstanceState(Parcelable state) {
    if (!(state instanceof CircleState)) {
      super.onRestoreInstanceState(state);
      return;
    }
    CircleState ss = (CircleState) state;
    super.onRestoreInstanceState(ss.getSuperState());
    this.circleColor = ss.color;
  }

  public CircleView(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView, 0, 0);
    setWillNotDraw(false);
    try {
      showText = typedArray.getBoolean(R.styleable.CircleView_text_include, true);
      textColor = typedArray.getColor(R.styleable.CircleView_text_color, Color.BLACK);
      circleColor = typedArray.getColor(R.styleable.CircleView_circle_color,
          getResources().getColor(R.color.colorPrimary));
      startText = typedArray.getString(R.styleable.CircleView_circle_start);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      typedArray.recycle();
    }
    init();
    setSaveEnabled(true);
    setSaveFromParentEnabled(true);
  }

  public int getCircleColor() {
    return circleColor;
  }

  public void setCircleColor(int circleColor) {
    this.circleColor = circleColor;
    invalidate();
    requestLayout();
  }

  public boolean isShowText() {
    return showText;
  }

  public void setShowText(boolean showText) {
    this.showText = showText;
    invalidate();
    requestLayout();
  }

  public void setStartText(String s) {
    this.startText = s;
    requestLayout();
    invalidate();
  }

  private String getStartText() {
    return this.startText;
  }

  public int getTextColor() {
    return textColor;
  }

  public void setTextColor(int textColor) {
    this.textColor = textColor;
    invalidate();
    requestLayout();
  }

  public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private void init() {
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(circleColor);
    paint.setAntiAlias(true);
    paint.setStyle(Paint.Style.FILL);
    textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    textPaint.setColor(textColor);
    textPaint.setAntiAlias(true);
    textPaint.setTextSize(getWidth() / 2);
    textPaint.setTextAlign(Paint.Align.CENTER);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int parentWidth = 150;
    switch (MeasureSpec.getMode(widthMeasureSpec)) {
      case MeasureSpec.AT_MOST:

        break;
      case MeasureSpec.EXACTLY:
        parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        break;
      case MeasureSpec.UNSPECIFIED:

        break;
    }
    int parentHeight = 150;
    switch (MeasureSpec.getMode(heightMeasureSpec)) {
      case MeasureSpec.AT_MOST:

        break;
      case MeasureSpec.EXACTLY:
        parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        break;
      case MeasureSpec.UNSPECIFIED:

        break;
    }
    circle_radius = parentWidth / 2;
    this.setMeasuredDimension(parentWidth, parentHeight);
  }

  @Override protected void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);
    int centerX = getWidth() / 2;
    int centerY = getHeight() / 2;
    canvas.drawCircle(centerX, centerY, circle_radius, paint);
    canvas.drawText(getStartText().substring(0, 1).toUpperCase(), centerX + getPaddingRight(),
        centerY + (centerY / 3), textPaint);
  }

  @Override public void draw(Canvas canvas) {
    super.draw(canvas);
    init();
    int centerX = getWidth() / 2;
    int centerY = getHeight() / 2;
    canvas.drawCircle(centerX, centerY, circle_radius, paint);
    canvas.drawText(getStartText().substring(0, 1).toUpperCase(), centerX + getPaddingRight(),
        centerY + (centerY / 3), textPaint);
  }

  static class CircleState extends BaseSavedState {
    int color;

    public CircleState(Parcel source) {
      super(source);
      color = source.readInt();
    }

    public CircleState(Parcelable superState) {
      super(superState);
    }

    @Override public void writeToParcel(Parcel out, int flags) {
      super.writeToParcel(out, flags);
      out.writeInt(color);
    }

    public static final Parcelable.Creator<CircleState> CREATOR = new Creator<CircleState>() {
      @Override public CircleState createFromParcel(Parcel parcel) {
        return new CircleState(parcel);
      }

      @Override public CircleState[] newArray(int i) {
        return new CircleState[i];
      }
    };
  }
}
