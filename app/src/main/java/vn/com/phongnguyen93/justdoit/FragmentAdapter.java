package vn.com.phongnguyen93.justdoit;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phongnguyen on 8/31/16.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
  private ArrayList<Fragment> fragments;
  private ArrayList<String> titles;

  public FragmentAdapter(FragmentManager fm,ArrayList<String> titles) {
    super(fm);
    if(titles==null || titles.size()<1) return;

    this.titles = titles;
    fragments = new ArrayList<>();

    for(int i= 0 ; i <titles.size();i++){
      fragments.add(TaskListFragment.newInstance(titles.get(i)));
    }
    notifyDataSetChanged();
  }

  @Override public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override public int getCount() {
    return fragments !=null ? fragments.size() : 0;
  }

  @Override public CharSequence getPageTitle(int position) {
      return titles.get(position);
  }

}
