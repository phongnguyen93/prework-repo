package vn.com.phongnguyen93.justdoit.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import vn.com.phongnguyen93.justdoit.R;
import vn.com.phongnguyen93.justdoit.utilities.Utilities;

public class SplashActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    findViewById(R.id.activity_splash).setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
        | View.SYSTEM_UI_FLAG_FULLSCREEN
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    findViewById(R.id.activity_splash).setBackground(Utilities.generateGradientDrawable(new int[] {
        ContextCompat.getColor(this, R.color.colorPrimary),
        ContextCompat.getColor(this, R.color.colorAccent)
    }));

    Handler handler = new Handler(Looper.getMainLooper());
    handler.postDelayed(new Runnable() {
      @Override public void run() {
       startActivity(new Intent(SplashActivity.this,HomeActivity.class));
      }
    }, 1500);
  }
}
