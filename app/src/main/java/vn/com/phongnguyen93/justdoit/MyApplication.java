package vn.com.phongnguyen93.justdoit;

import android.app.Application;
import vn.com.phongnguyen93.justdoit.ui_view.FontsOverride;

/**
 * Created by phongnguyen on 2/6/17.
 */

public class MyApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    FontsOverride.setDefaultFont(this, "DEFAULT", "Montserrat-Regular.ttf");
    FontsOverride.setDefaultFont(this, "MONOSPACE", "Montserrat-Regular.ttf");
    FontsOverride.setDefaultFont(this, "SERIF", "Montserrat-Regular.ttf");
    FontsOverride.setDefaultFont(this, "SANS_SERIF", "Montserrat-Regular.ttf");
  }
}
