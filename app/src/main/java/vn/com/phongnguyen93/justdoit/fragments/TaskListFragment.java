package vn.com.phongnguyen93.justdoit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

import vn.com.phongnguyen93.justdoit.R;
import vn.com.phongnguyen93.justdoit.model.Task;
import vn.com.phongnguyen93.justdoit.adapters.TaskAdapter;
import vn.com.phongnguyen93.justdoit.repository.TaskRepository;
import vn.com.phongnguyen93.justdoit.ui_view.VerticalSpaceItemDecoration;
import vn.com.phongnguyen93.justdoit.activities.EditActivity;
import vn.com.phongnguyen93.justdoit.helper.OnStartDragListener;
import vn.com.phongnguyen93.justdoit.helper.SimpleItemTouchHelperCallback;

/**
 * Created by phongnguyen on 2/4/17.
 */

public class TaskListFragment extends Fragment implements TaskAdapter.ItemInteractionCallback {
  private TaskAdapter doneTaskAdapter;
  private TaskAdapter todoTaskAdapter;
  private String queryDate;
  public static final String QUERY_DATE_KEY = "queryDate";
  private RecyclerView rvDoneList;
  private LinearLayout todoList;
  private LinearLayout doneList;
  private TextView tvTodoSum;
  private TextView tvDoneSum;
  private RecyclerView rvTodoList;
  private ItemTouchHelper mItemTouchHelper;
  private CoordinatorLayout parentView;

  private ArrayList<Task> doneTasks;
  private ArrayList<Task> todoTasks;

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
    parentView = (CoordinatorLayout)v.findViewById(R.id.coord_layout);
    VerticalSpaceItemDecoration itemSpace = new VerticalSpaceItemDecoration(16);

    FloatingActionButton fabNewTask = (FloatingActionButton) v.findViewById(R.id.fab_add);

    fabNewTask.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(new Intent(getContext(), EditActivity.class));
      }
    });

    todoList = (LinearLayout) v.findViewById(R.id.todo_list_layout);
    doneList = (LinearLayout) v.findViewById(R.id.done_list_layout);

    tvDoneSum = (TextView) v.findViewById(R.id.tv_done_count);
    tvTodoSum = (TextView) v.findViewById(R.id.tv_todo_count);

    todoTaskAdapter = new TaskAdapter(getContext(), this, new OnStartDragListener() {
      @Override public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
      }
    });

    doneTaskAdapter = new TaskAdapter(getContext(), this, new OnStartDragListener() {
      @Override public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        //mItemTouchHelper.startDrag(viewHolder);
      }
    });

    ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(doneTaskAdapter);
    mItemTouchHelper = new ItemTouchHelper(callback);

    rvTodoList = (RecyclerView) v.findViewById(R.id.rv_todo_task_list);
    rvTodoList.setNestedScrollingEnabled(false);
    RecyclerView.LayoutManager todoLayoutManager =
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    todoLayoutManager.setAutoMeasureEnabled(true);
    rvTodoList.setLayoutManager(todoLayoutManager);
    rvTodoList.setAdapter(todoTaskAdapter);
    rvTodoList.addItemDecoration(itemSpace);
    mItemTouchHelper.attachToRecyclerView(rvTodoList);

    rvDoneList = (RecyclerView) v.findViewById(R.id.rv_done_task_list);
    RecyclerView.LayoutManager doneLayoutManager =
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    doneLayoutManager.setAutoMeasureEnabled(true);
    rvDoneList.setNestedScrollingEnabled(false);
    rvDoneList.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    rvDoneList.setAdapter(doneTaskAdapter);
    rvDoneList.addItemDecoration(itemSpace);
    return v;
  }

  @Override public void onResume() {
    super.onResume();
    invalidateTodoTasks();
    invalidateDoneTasks();
  }

  @Override public void onItemLongClick(final int position, final Task task) {

  }

  @Override public void onItemClick(Task task) {
    Intent t = new Intent(getContext(), EditActivity.class);
    t.putExtra("task", task);
    startActivity(t);
  }

  @Override public void onItemSwipeRight(Task task, final int position) {
    task.setStatus(Task.TASK_DONE);
    TaskRepository.getsInstance(getContext())
        .updateTask(task, new TaskRepository.RepositoryResponse() {
          @Override public void onSuccess() {
            if (position >= 0) {
              rvTodoList.getLayoutManager().removeViewAt(position);
              todoTaskAdapter.removeItem(position);

              invalidateTodoTasks();
              invalidateDoneTasks();
            }
          }

          @Override public void onError() {

          }
        });
  }

  private void invalidateDoneTasks() {
    if (queryDate == null) return;
    doneTasks = TaskRepository.getsInstance(getContext()).getTasks(queryDate, Task.TASK_DONE);
    if (doneTasks.size() == 0) {
      doneList.setVisibility(View.GONE);
    } else {
      doneList.setVisibility(View.VISIBLE);
    }
    tvDoneSum.setText(String.format(getString(R.string.done_header_msg), doneTasks.size()));
    doneTaskAdapter.setTasks(doneTasks);
  }

  private void invalidateTodoTasks() {
    if (queryDate == null) return;
    todoTasks = TaskRepository.getsInstance(getContext()).getTasks(queryDate, Task.TASK_TODO);
    if (todoTasks.size() == 0) {
      todoList.setVisibility(View.GONE);
    } else {
      todoList.setVisibility(View.VISIBLE);
    }
    tvTodoSum.setText(String.format(getString(R.string.todo_header_msg), todoTasks.size()));
    todoTaskAdapter.setTasks(todoTasks);
  }

  @Override public void onItemEditClick(Task task, int position) {
    Intent t = new Intent(getContext(), EditActivity.class);
    t.putExtra("task", task);
    startActivity(t);
  }

  @Override public void onItemRemoveClick(final Task task, final int position) {
    TaskRepository.getsInstance(getContext())
        .removeTask(task.getId(), new TaskRepository.RepositoryResponse() {
          @Override public void onSuccess() {

            for (Task task1 : doneTasks) {
              if (task1.getId() == task.getId()) {
                rvDoneList.getLayoutManager().removeViewAt(position);
                doneTaskAdapter.removeItem(position);
              } else {
                rvTodoList.getLayoutManager().removeViewAt(position);
                todoTaskAdapter.removeItem(position);
              }
            }

            invalidateTodoTasks();
            invalidateDoneTasks();


          }

          @Override public void onError() {

          }
        });
  }

}
