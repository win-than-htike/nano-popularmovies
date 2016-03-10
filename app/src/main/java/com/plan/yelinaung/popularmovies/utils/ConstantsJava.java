package com.plan.yelinaung.popularmovies.utils;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by user on 3/6/16.
 */
public class ConstantsJava {
  private int days;

  @IntDef({ SUNDAY, SATURDAY }) @Retention(RetentionPolicy.SOURCE) public @interface WeekDays {
  }

  public static final int SUNDAY = 1;
  public static final int SATURDAY = 2;

  @ConstantsJava.WeekDays public int getWeekdays() {
    return days;
  }

  public void setWeekdays(@WeekDays int weekdays) {
    this.days = weekdays;
  }
}
