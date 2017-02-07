package vn.com.phongnguyen93.justdoit.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.lzyzsd.circleprogress.ArcProgress;
import java.util.ArrayList;
import java.util.Calendar;
import vn.com.phongnguyen93.justdoit.utilities.PreferencesUtility;
import vn.com.phongnguyen93.justdoit.R;
import vn.com.phongnguyen93.justdoit.model.Task;
import vn.com.phongnguyen93.justdoit.repository.TaskRepository;
import vn.com.phongnguyen93.justdoit.utilities.Utilities;

/**
 * Created by phongnguyen on 2/7/17.
 */

public class SummaryFragment extends Fragment {
  private TextView tvGreeting;
  private TextView tvTodayDate;
  private TextView tvUpComingTask;
  private TextView tvTaskCount;
  private ArcProgress arcTaskProgress;
  private ArrayList<Task> todayTasks;
  private ArrayList<Task> doneTasks;
  private ArrayList<Task> todoTasks;

  public static SummaryFragment newInstance() {
    Bundle args = new Bundle();
    SummaryFragment fragment = new SummaryFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_task_summary, container, false);

    tvGreeting = (TextView) v.findViewById(R.id.tv_greeting);
    tvTodayDate = (TextView) v.findViewById(R.id.tv_today_date);
    tvUpComingTask = (TextView) v.findViewById(R.id.tv_upcoming_task);
    tvTaskCount = (TextView) v.findViewById(R.id.tv_task_count);
    arcTaskProgress = (ArcProgress) v.findViewById(R.id.arc_progress);

    String userName =
        PreferencesUtility.getInstance(getContext()).getString(PreferencesUtility.PRE_KEY_USER);
    if (userName != null) {
      tvGreeting.setText(String.format(getString(R.string.greeting_msg), userName));
    }

    return v;
  }

  @Override public void onResume() {
    super.onResume();
    invalidateUI();
  }

  private void invalidateUI() {
    String queryDate = Utilities.getDate(Calendar.getInstance().getTime());
    todayTasks = TaskRepository.getsInstance(getContext()).getTasks(queryDate, Task.ALL_TASK);

    if (todayTasks != null && todayTasks.size() > 0) {
      int[] priorityCount = getTaskPriorityCount(todayTasks);
      tvUpComingTask.setText(
          String.format(getString(R.string.upcoming_msg), todayTasks.get(0).getLabel(),
              todayTasks.get(0).getTime()));

      tvTodayDate.setText(Utilities.getTodayDate());

      arcTaskProgress.setMax(100);
      arcTaskProgress.setProgress(doneTasks.size() / todayTasks.size() * 100);

      tvTaskCount.setText(Html.fromHtml(
          String.format(getString(R.string.task_count_msg), priorityCount[0], priorityCount[1],
              priorityCount[2])));
    } else {
      tvUpComingTask.setVisibility(View.GONE);
      tvTaskCount.setText(Html.fromHtml(
          String.format(getString(R.string.task_count_msg), 0, 0,
              0)));
    }
  }

  private int[] getTaskPriorityCount(ArrayList<Task> tasks) {
    ArrayList<Task> low = new ArrayList<>();
    ArrayList<Task> medium = new ArrayList<>();
    ArrayList<Task> high = new ArrayList<>();
    todoTasks = new ArrayList<>();
    doneTasks = new ArrayList<>();

    for (Task task : tasks) {
      if (task.getStatus() == Task.TASK_DONE) {
        doneTasks.add(task);
      } else {
        todoTasks.add(task);
      }

      switch (task.getPriority()) {
        case 0:
          high.add(task);
          break;
        case 1:
          medium.add(task);
          break;
        case 2:
          low.add(task);
          break;
      }
    }
    return new int[] { high.size(), medium.size(), low.size() };
  }
}
