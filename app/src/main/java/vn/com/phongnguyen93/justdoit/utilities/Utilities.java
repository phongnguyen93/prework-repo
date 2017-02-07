package vn.com.phongnguyen93.justdoit.utilities;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.view.Display;
import android.view.WindowManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by phongnguyen on 2/4/17.
 */

public class Utilities {

  public static int getScreenHeight(Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    return size.y;
  }

  public static int getScreenWidth(Context c){
    WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    return size.x;
  }


  public static GradientDrawable generateGradientDrawable(int[] colorList) {
    GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colorList);
    gd.setCornerRadius(0f);
    return gd;
  }

  public static int dp2px(Context context, float dipValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dipValue * scale + 0.5f);
  }

  public static String getTodayDate(){
    DateFormat dateFormat2 = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
    dateFormat2.setTimeZone(TimeZone.getTimeZone("GMT"));

    Calendar calendar = Calendar.getInstance();
    Date today = calendar.getTime();
    return dateFormat2.format(today);
  }

  public static String getDate(Date date){
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    return dateFormatter.format(date);
  }
}
