package vn.com.phongnguyen93.justdoit.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.daimajia.swipe.SwipeLayout;
import java.util.ArrayList;
import java.util.Collections;
import vn.com.phongnguyen93.justdoit.R;
import vn.com.phongnguyen93.justdoit.model.Task;
import vn.com.phongnguyen93.justdoit.utilities.Utilities;
import vn.com.phongnguyen93.justdoit.helper.ItemTouchHelperAdapter;
import vn.com.phongnguyen93.justdoit.helper.ItemTouchHelperViewHolder;
import vn.com.phongnguyen93.justdoit.helper.OnStartDragListener;
import vn.com.phongnguyen93.justdoit.ui_view.SmoothCheckBox;

/**
 * Created by phongnguyen on 2/4/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>
    implements ItemTouchHelperAdapter {
  private Context context;
  private ArrayList<Task> tasks;
  private ItemInteractionCallback callback;
  private final OnStartDragListener mDragStartListener;

  @Override public boolean onItemMove(int fromPosition, int toPosition) {
    Collections.swap(tasks, fromPosition, toPosition);
    notifyItemMoved(fromPosition, toPosition);
    return true;
  }

  @Override public void onItemDismiss(int position) {
    tasks.remove(position);
    notifyItemRemoved(position);
  }


  public interface ItemInteractionCallback {
    void onItemLongClick(int position, Task task);

    void onItemClick(Task task);

    void onItemSwipeRight(Task task, int position);

    void onItemEditClick(Task task, int position);

    void onItemRemoveClick(Task task, int position);
  }

  public TaskAdapter(Context context, ItemInteractionCallback callback,
      OnStartDragListener dragStartListener) {
    this.context = context;
    this.callback = callback;
    this.mDragStartListener = dragStartListener;
  }

  public void setTasks(ArrayList<Task> tasks) {
    if (tasks != null && tasks.size() > 0) {
      this.tasks = tasks;
      notifyDataSetChanged();
    }
  }

  public void removeItem(int position) {
    tasks.remove(position);
    notifyItemRemoved(position);
  }

  @Override public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_layout, parent, false);
    return new TaskViewHolder(v);
  }

  @Override public void onBindViewHolder(TaskViewHolder holder, final int position) {
    holder.bindView(tasks.get(position));
    //holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
    //  @Override public boolean onLongClick(View view) {
    //    callback.onItemLongClick(position, tasks.get(position));
    //    return true;
    //  }
    //});

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        //callback.onItemClick(tasks.get(position));
      }
    });
  }

  @Override public int getItemCount() {
    return tasks != null ? tasks.size() : 0;
  }

  class TaskViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    private TextView tvLabel;
    private TextView tvDesc;
    private TextView tvTime;
    private SwipeLayout taskLayout;
    private ImageView imgEdit;
    private ImageView imgRemove;
    private ImageView imgTag;
    private LinearLayout doneLayout;
    private SmoothCheckBox cbDone;
    private ImageView imgYes;
    private ImageView imgNo;

    TaskViewHolder(View itemView) {
      super(itemView);
      tvLabel = (TextView) itemView.findViewById(R.id.tv_task_label);
      tvDesc = (TextView) itemView.findViewById(R.id.tv_task_description);
      tvTime = (TextView) itemView.findViewById(R.id.tv_task_time);
      imgEdit = (ImageView) itemView.findViewById(R.id.img_edit);
      imgRemove = (ImageView) itemView.findViewById(R.id.img_remove);
      imgTag = (ImageView) itemView.findViewById(R.id.img_tag);
      imgTag.setColorFilter(ContextCompat.getColor(context, R.color.white));
      taskLayout = (SwipeLayout) itemView.findViewById(R.id.item_layout);
      cbDone = (SmoothCheckBox)itemView.findViewById(R.id.cb_done);
      imgYes = (ImageView)itemView.findViewById(R.id.btn_ok);
      imgYes.setColorFilter(ContextCompat.getColor(context,R.color.success));

      imgNo =(ImageView)itemView.findViewById(R.id.btn_cancel);
      imgNo.setColorFilter(ContextCompat.getColor(context,R.color.error));
      imgNo.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          taskLayout.close(true);
        }
      });

      //set show mode.
      taskLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
      taskLayout.setClickToClose(true);
      doneLayout = (LinearLayout) itemView.findViewById(R.id.done_layout);
      doneLayout.setBackground(Utilities.generateGradientDrawable(new int[] {
          ContextCompat.getColor(context, R.color.colorPrimary),
          ContextCompat.getColor(context, R.color.colorAccent)
      }));

      itemView.findViewById(R.id.display_layout).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          taskLayout.open(true, SwipeLayout.DragEdge.Right);
        }
      });
    }

    void bindView(final Task item) {
      if (item != null) {
        tvLabel.setText(item.getLabel());
        tvDesc.setText(item.getDescription());
        tvTime.setText(item.getTime());
        imgTag.setImageDrawable(ContextCompat.getDrawable(context, item.getTag()));

        switch (item.getPriority()) {
          case 0:
            imgTag.setBackgroundColor(ContextCompat.getColor(context, R.color.error));
            break;
          case 1:
            imgTag.setBackgroundColor(ContextCompat.getColor(context, R.color.medium));
            break;
          case 2:
            imgTag.setBackgroundColor(ContextCompat.getColor(context, R.color.success));
            break;
        }

        imgYes.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View view) {
            callback.onItemRemoveClick(item, tasks.indexOf(item));
          }
        });

        if (item.getStatus() == Task.TASK_DONE) {
          //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
          taskLayout.addDrag(SwipeLayout.DragEdge.Right, itemView.findViewById(R.id.edit_layout));
          cbDone.setChecked(true);
        } else {
          //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
          taskLayout.addDrag(SwipeLayout.DragEdge.Right, itemView.findViewById(R.id.edit_layout));
          taskLayout.addDrag(SwipeLayout.DragEdge.Left, itemView.findViewById(R.id.done_layout));
          taskLayout.addDrag(SwipeLayout.DragEdge.Bottom,itemView.findViewById(R.id.confirm_layout));
          cbDone.setChecked(false);
          cbDone.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
              if(isChecked){
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                  @Override public void run() {
                    taskLayout.open(true, SwipeLayout.DragEdge.Left);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                      @Override public void run() {
                        callback.onItemSwipeRight(item, tasks.indexOf(item));
                      }
                    }, 400);
                  }
                }, 1000);
              }
            }
          });
        }



        taskLayout.addRevealListener(R.id.done_layout, new SwipeLayout.OnRevealListener() {
          @Override public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction,
              int distance) {
            if (edge == SwipeLayout.DragEdge.Left && distance > child.getWidth() / 2) {
              Handler handler = new Handler(Looper.getMainLooper());
              handler.postDelayed(new Runnable() {
                @Override public void run() {
                  callback.onItemSwipeRight(item, tasks.indexOf(item));
                }
              }, 1000);
            }
          }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View view) {
            callback.onItemEditClick(item, tasks.indexOf(item));
          }
        });

        imgRemove.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View view) {
            taskLayout.open(true, SwipeLayout.DragEdge.Bottom);
          }
        });
      }
    }

    @Override public void onItemSelected() {

    }

    @Override public void onItemClear() {
    }
  }

}
