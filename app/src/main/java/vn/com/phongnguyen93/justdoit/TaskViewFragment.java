package vn.com.phongnguyen93.justdoit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

/**
 * Created by phongnguyen on 2/4/17.
 */

public class TaskViewFragment extends Fragment {

  private FragmentAdapter fragmentAdapter;
  private ViewPager viewPager;
  private SmartTabLayout tabLayout;

  public static TaskViewFragment newInstance() {
    Bundle args = new Bundle();
    TaskViewFragment fragment = new TaskViewFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_task_view, container, false);

    FrameLayout summaryLayout = (FrameLayout) v.findViewById(R.id.summary_content);
    summaryLayout.getLayoutParams().height = Utilities.getScreenHeight(getContext()) / 3;
    summaryLayout.requestLayout();

    viewPager = (ViewPager) v.findViewById(R.id.view_pager);

    tabLayout = (SmartTabLayout) v.findViewById(R.id.tab_layout);
    tabLayout.setDistributeEvenly(true);


    return v;
  }

  @Override public void onResume() {
    super.onResume();
    fragmentAdapter = new FragmentAdapter(getChildFragmentManager(),
        TaskRepository.getsInstance(getContext()).getAllItemDate());
    viewPager.setAdapter(fragmentAdapter);
    tabLayout.setViewPager(viewPager);
  }
}
