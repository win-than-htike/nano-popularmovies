package com.plan.yelinaung.popularmovies.utils;

import android.graphics.Color;
import java.util.Random;

/**
 * Created by user on 2/26/16.
 */
public class ColorRandom {

  public static int[] c = { Color.RED, Color.LTGRAY, Color.YELLOW };

  public static int random() {
    Random random = new Random();
    return c[random.nextInt(3)];
  }
}
