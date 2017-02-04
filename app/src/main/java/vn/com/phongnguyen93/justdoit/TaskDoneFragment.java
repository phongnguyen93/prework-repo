package vn.com.phongnguyen93.justdoit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by phongnguyen on 2/4/17.
 */

public class TaskDoneFragment extends Fragment {

  public static TaskDoneFragment newInstance() {
     Bundle args = new Bundle();
     TaskDoneFragment fragment = new TaskDoneFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View  v = inflater.inflate(R.layout.fragment_task_done,container,false);
    return v;
  }
}
