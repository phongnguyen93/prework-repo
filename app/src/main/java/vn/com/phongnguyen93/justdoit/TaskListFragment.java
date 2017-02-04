package vn.com.phongnguyen93.justdoit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by phongnguyen on 2/4/17.
 */

public class TaskListFragment extends Fragment implements TaskAdapter.ItemInteractionCallback {
  private TaskAdapter taskAdapter;
  private String queryDate;
  public static final String QUERY_DATE_KEY = "queryDate";
  private RecyclerView rvTaskList;

  public static TaskListFragment newInstance(String queryDate) {
    Bundle args = new Bundle();
    TaskListFragment fragment = new TaskListFragment();
    args.putString(QUERY_DATE_KEY, queryDate);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      queryDate = getArguments().getString(QUERY_DATE_KEY);
    }
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_task_list, container, false);

    VerticalSpaceItemDecoration itemSpace = new VerticalSpaceItemDecoration(16);

    FloatingActionButton fabNewTask = (FloatingActionButton) v.findViewById(R.id.fab_add);
    fabNewTask.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(new Intent(getContext(), EditActivity.class));
      }
    });

    taskAdapter = new TaskAdapter(this);
    rvTaskList = (RecyclerView) v.findViewById(R.id.rv_task_list);
    rvTaskList.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    rvTaskList.setAdapter(taskAdapter);
    rvTaskList.addItemDecoration(itemSpace);
    return v;
  }

  @Override public void onResume() {
    super.onResume();
    taskAdapter.setTasks(TaskRepository.getsInstance(getContext()).getTasks(queryDate));
  }

  @Override public void onItemLongClick(final int position, final Task task) {
    TaskRepository.getsInstance(getContext())
        .removeTask(task.getId(), new TaskRepository.RepositoryResponse() {
          @Override public void onSuccess() {
            rvTaskList.getLayoutManager().removeViewAt(position);
            taskAdapter.removeItem(position);
          }

          @Override public void onError() {

          }
        });
  }

  @Override public void onItemClick(Task task) {
    Intent t =  new Intent(getContext(),EditActivity.class);
    t.putExtra("task",task);
    startActivity(t);
  }
}
