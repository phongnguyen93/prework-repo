package vn.com.phongnguyen93.justdoit;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by phongnguyen on 2/4/17.
 */

public class Utilities {

  public static int getScreenHeight(Context context){
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    return size.y;
  }

}
