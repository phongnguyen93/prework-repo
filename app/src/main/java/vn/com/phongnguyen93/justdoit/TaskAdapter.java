package vn.com.phongnguyen93.justdoit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by phongnguyen on 2/4/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
  private ArrayList<Task> tasks;
  private ItemInteractionCallback callback;

  public interface ItemInteractionCallback{
    void onItemLongClick(int position, Task task);
    void onItemClick(Task task);
  }

  public TaskAdapter(ItemInteractionCallback callback){
    this.callback = callback;
  }

  public void setTasks(ArrayList<Task> tasks){
    if(tasks!=null && tasks.size()>0){
      this.tasks = tasks;
      notifyDataSetChanged();
    }
  }

  public void removeItem(int position){
    tasks.remove(position);
    notifyItemRemoved(position);
  }

  @Override public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_layout,parent,false);
    return new TaskViewHolder(v);
  }

  @Override public void onBindViewHolder(TaskViewHolder holder, final int position) {
    holder.bindView(tasks.get(position));
    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override public boolean onLongClick(View view) {
        callback.onItemLongClick(position, tasks.get(position));
        return true;
      }
    });

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        callback.onItemClick(tasks.get(position));
      }
    });
  }

  @Override public int getItemCount() {
    return tasks!=null ? tasks.size() : 0;
  }

  class TaskViewHolder extends RecyclerView.ViewHolder{
    private TextView tvLabel;
    private TextView tvDesc;


    TaskViewHolder(View itemView) {
      super(itemView);
      tvLabel = (TextView)itemView.findViewById(R.id.tv_task_label);
      tvDesc = (TextView)itemView.findViewById(R.id.tv_task_description);
    }

    void bindView(Task item){
      if(item!=null){
        tvLabel.setText(item.getLabel());
        tvDesc.setText(item.getDescription());
      }
    }
  }
}
