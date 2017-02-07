package vn.com.phongnguyen93.justdoit.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import vn.com.phongnguyen93.justdoit.adapters.FragmentAdapter;
import vn.com.phongnguyen93.justdoit.R;
import vn.com.phongnguyen93.justdoit.repository.TaskRepository;
import vn.com.phongnguyen93.justdoit.utilities.Utilities;

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

    tabLayout.setBackground(Utilities.generateGradientDrawable(new int[] {
        ContextCompat.getColor(getContext(), R.color.colorPrimary),
        ContextCompat.getColor(getContext(), R.color.colorAccent)
    }));

    getChildFragmentManager().beginTransaction()
        .replace(R.id.summary_container, SummaryFragment.newInstance())
        .commit();

    return v;
  }

  @Override public void onResume() {
    super.onResume();

    if (TaskRepository.getsInstance(getContext()).getAllItemDate().size() > 0) {
      tabLayout.setVisibility(View.VISIBLE);
      viewPager.setVisibility(View.VISIBLE);
      fragmentAdapter = new FragmentAdapter(getChildFragmentManager(),
          TaskRepository.getsInstance(getContext()).getAllItemDate());
      viewPager.setAdapter(fragmentAdapter);
      tabLayout.setViewPager(viewPager);
    } else {
      tabLayout.setVisibility(View.GONE);
      viewPager.setVisibility(View.GONE);
      getFragmentManager().beginTransaction()
          .replace(R.id.task_view_content, EmptyFragment.newInstance())
          .commit();
    }
  }
}
