package vn.com.phongnguyen93.justdoit.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by phongnguyen on 2/4/17.
 */

public class Task implements Parcelable {
  public static final int TASK_TODO = 0;
  public static final int TASK_DONE = 1;
  public static final int ALL_TASK = 2;

  private int id;
  private String label;
  private String description;
  private String date;
  private String time;
  private int priority;
  private int status;
  private int tag;

  public Task(){

  }

  protected Task(Parcel in) {
    id = in.readInt();
    label = in.readString();
    description = in.readString();
    date = in.readString();
    time = in.readString();
    priority = in.readInt();
    status = in.readInt();
    tag = in.readInt();
  }

  public static final Creator<Task> CREATOR = new Creator<Task>() {
    @Override public Task createFromParcel(Parcel in) {
      return new Task(in);
    }

    @Override public Task[] newArray(int size) {
      return new Task[size];
    }
  };

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDueDate() {
    return date;
  }

  public void setDueDate(String due_date) {
    this.date = due_date;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(id);
    parcel.writeString(label);
    parcel.writeString(description);
    parcel.writeString(date);
    parcel.writeString(time);
    parcel.writeInt(priority);
    parcel.writeInt(status);
    parcel.writeInt(tag);
  }

  public int getTag() {
    return tag;
  }

  public void setTag(int tag) {
    this.tag = tag;
  }
}
