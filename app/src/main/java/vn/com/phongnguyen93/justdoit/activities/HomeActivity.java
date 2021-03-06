package vn.com.phongnguyen93.justdoit.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import vn.com.phongnguyen93.justdoit.utilities.PreferencesUtility;
import vn.com.phongnguyen93.justdoit.R;
import vn.com.phongnguyen93.justdoit.fragments.TaskViewFragment;
import vn.com.phongnguyen93.justdoit.fragments.WelcomeFragment;

public class HomeActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {
  private Toolbar toolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    toolbar = (Toolbar) findViewById(R.id.MyToolbar);
    setSupportActionBar(toolbar);

    toolbar.setNavigationIcon(R.drawable.ic_menu_button_of_three_horizontal_lines);
    getSupportActionBar().setTitle(null);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.setScrimColor(Color.parseColor("#33000000"));
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    toggle.setDrawerIndicatorEnabled(false);
    toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (!drawer.isDrawerOpen(GravityCompat.START)) {
          drawer.openDrawer(GravityCompat.START, true);
        }
      }
    });
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

  }

  @Override protected void onResume() {
    super.onResume();
    String user = PreferencesUtility.getInstance(this).getString(PreferencesUtility.PRE_KEY_USER);
    if(user==null || TextUtils.isEmpty(user)){
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.main_container, WelcomeFragment.newInstance())
          .commit();
    }else
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.main_container, TaskViewFragment.newInstance())
          .commit();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_add) {
      startActivity(new Intent(HomeActivity.this,EditActivity.class));
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    return true;
  }

  @SuppressWarnings("StatementWithEmptyBody") @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera) {
      // Handle the camera action
    } else if (id == R.id.nav_gallery) {

    } else if (id == R.id.nav_slideshow) {

    } else if (id == R.id.nav_manage) {

    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
