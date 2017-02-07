package vn.com.phongnguyen93.justdoit.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import vn.com.phongnguyen93.justdoit.model.Task;

/**
 * Created by phongnguyen on 2/4/17.
 */

public class TaskRepository extends SQLiteOpenHelper implements RepositoryInterface {
  private static TaskRepository sInstance;

  // Database Info
  private static final String DATABASE_NAME = "JustDoItDatabase";
  private static final int DATABASE_VERSION = 1;

  // Table Names
  private static final String TABLE_TASKS = "tasks";

  // Task Table Columns
  private static final String KEY_TASK_ID = "id";
  private static final String KEY_TASK_LABEL = "label";
  private static final String KEY_TASK_DESCRIPTION = "description";
  private static final String KEY_TASK_DATE = "date";
  private static final String KEY_TASK_PRIORITY = "priority";
  private static final String KEY_TASK_STATUS = "status";
  private static final String KEY_TASK_TIME = "time";
  private static final String KEY_TASK_TAG = "tag";

  public interface RepositoryResponse {
    void onSuccess();

    void onError();
  }

  public static synchronized TaskRepository getsInstance(Context context) {
    if (sInstance == null) {
      synchronized (TaskRepository.class) {
        if (sInstance == null) {
          sInstance = new TaskRepository(context);
        }
      }
    }
    return sInstance;
  }

  private TaskRepository(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onConfigure(SQLiteDatabase db) {
    super.onConfigure(db);
    db.setForeignKeyConstraintsEnabled(true);
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS +
        "(" +
        KEY_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + // Define a primary key
        KEY_TASK_LABEL + " TEXT," + // Define a foreign key
        KEY_TASK_DESCRIPTION + " TEXT," +
        KEY_TASK_DATE + " TEXT," +
        KEY_TASK_TIME + " TEXT," +
        KEY_TASK_PRIORITY + " INTEGER," +
        KEY_TASK_TAG + " INTEGER," +
        KEY_TASK_STATUS + " INTEGER" +
        ")";
    sqLiteDatabase.execSQL(CREATE_TASKS_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    if (oldVersion != newVersion) {
      // Simplest implementation is to drop all old tables and recreate them
      sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
      onCreate(sqLiteDatabase);
    }
  }

  /**
   * Add new row to database with Task metadata
   *
   * @param item Task object
   */
  private void addItem(Task item, RepositoryResponse callback) {
    if (item == null) return;
    SQLiteDatabase db = getWritableDatabase();

    db.beginTransaction();

    try {
      ContentValues values = new ContentValues();
      values.put(KEY_TASK_LABEL, item.getLabel());
      values.put(KEY_TASK_DESCRIPTION, item.getDescription());
      values.put(KEY_TASK_DATE, item.getDueDate());
      values.put(KEY_TASK_PRIORITY, item.getPriority());
      values.put(KEY_TASK_STATUS, item.getStatus());
      values.put(KEY_TASK_TIME, item.getTime());
      values.put(KEY_TASK_TAG, item.getTag());

      long id = db.insertOrThrow(TABLE_TASKS, null, values);
      if (id > 0) {
        db.setTransactionSuccessful();
        callback.onSuccess();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      callback.onError();
    } finally {
      db.endTransaction();
    }
  }

  /**
   * Query single item from database -> convert to Task Object
   *
   * @param id query task id
   * @return converted Task object
   */
  private Task getItemById(int id) {
    Task task = new Task();
    SQLiteDatabase db = getReadableDatabase();

    String SQL_QUERY_TASK_BY_ID =
        String.format("SELECT * FROM %s WHERE %s = %s LIMIT 1", TABLE_TASKS, KEY_TASK_ID,
            String.valueOf(id));

    Cursor cursor = db.rawQuery(SQL_QUERY_TASK_BY_ID, null);
    try {
      if (cursor.moveToFirst()) {
        do {
          task.setId(cursor.getInt(cursor.getColumnIndex(KEY_TASK_ID)));
          task.setLabel(cursor.getString(cursor.getColumnIndex(KEY_TASK_LABEL)));
          task.setDescription(cursor.getString(cursor.getColumnIndex(KEY_TASK_DESCRIPTION)));
          task.setDueDate(cursor.getString(cursor.getColumnIndex(KEY_TASK_DATE)));
          task.setTime(cursor.getString(cursor.getColumnIndex(KEY_TASK_TIME)));
          task.setPriority(cursor.getInt(cursor.getColumnIndex(KEY_TASK_PRIORITY)));
          task.setStatus(cursor.getInt(cursor.getColumnIndex(KEY_TASK_STATUS)));
          task.setTag(cursor.getInt(cursor.getColumnIndex(KEY_TASK_TAG)));
        } while (cursor.moveToNext());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
    }
    return task;
  }

  private ArrayList<Task> getAllItemByDate(String date, int taskStatus) {
    ArrayList<Task> tasks = new ArrayList<>();
    SQLiteDatabase db = getReadableDatabase();

    String SQL_QUERY_TASKS_BY_DATE =
        String.format("SELECT * FROM %s WHERE %s LIKE '%s' AND %s = %s ORDER BY %s ASC ",
            TABLE_TASKS, KEY_TASK_DATE, date, KEY_TASK_STATUS, taskStatus, KEY_TASK_STATUS);

    if(taskStatus == Task.ALL_TASK)
      SQL_QUERY_TASKS_BY_DATE =   String.format("SELECT * FROM %s WHERE %s LIKE '%s' ORDER BY %s ASC",
          TABLE_TASKS, KEY_TASK_DATE, date, KEY_TASK_STATUS);

    Cursor cursor = db.rawQuery(SQL_QUERY_TASKS_BY_DATE, null);
    try {
      if (cursor.moveToFirst()) {
        do {
          Task task = new Task();
          task.setId(cursor.getInt(cursor.getColumnIndex(KEY_TASK_ID)));
          task.setLabel(cursor.getString(cursor.getColumnIndex(KEY_TASK_LABEL)));
          task.setDescription(cursor.getString(cursor.getColumnIndex(KEY_TASK_DESCRIPTION)));
          task.setDueDate(cursor.getString(cursor.getColumnIndex(KEY_TASK_DATE)));
          task.setTime(cursor.getString(cursor.getColumnIndex(KEY_TASK_TIME)));
          task.setPriority(cursor.getInt(cursor.getColumnIndex(KEY_TASK_PRIORITY)));
          task.setStatus(cursor.getInt(cursor.getColumnIndex(KEY_TASK_STATUS)));
          task.setTag(cursor.getInt(cursor.getColumnIndex(KEY_TASK_TAG)));

          tasks.add(task);
        } while (cursor.moveToNext());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
    }
    return tasks;
  }

  private void deleteItemById(int id, RepositoryResponse callback) {
    SQLiteDatabase db = getWritableDatabase();
    db.beginTransaction();
    try {
      String select = KEY_TASK_ID + " = ? ";
      String[] arg = { String.valueOf(id) };
      int result = db.delete(TABLE_TASKS, select, arg);
      if (result > 0) {
        db.setTransactionSuccessful();
        callback.onSuccess();
      }
    } catch (Exception ex) {
      callback.onError();
      ex.printStackTrace();
    } finally {
      db.endTransaction();
    }
  }

  private void deleteItemsById(ArrayList<Integer> ids, RepositoryResponse callback) {
    SQLiteDatabase db = getWritableDatabase();
    db.beginTransaction();
    int result = 0;
    try {
      for (Integer id : ids) {
        String select = KEY_TASK_ID + " = ? ";
        String[] arg = { String.valueOf(id) };
        result += db.delete(TABLE_TASKS, select, arg);
      }
      if (result > 0) {
        db.setTransactionSuccessful();
        callback.onSuccess();
      }
    } catch (Exception ex) {
      callback.onError();
      ex.printStackTrace();
    } finally {
      db.endTransaction();
    }
  }

  private void deleteAllItem() {
    SQLiteDatabase db = getWritableDatabase();
    db.beginTransaction();
    try {
      db.delete(TABLE_TASKS, null, null);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      db.endTransaction();
    }
  }

  private void updateItem(Task item, RepositoryResponse callback) {
    SQLiteDatabase db = getWritableDatabase();
    db.beginTransaction();
    try {
      ContentValues values = new ContentValues();
      values.put(KEY_TASK_LABEL, item.getLabel());
      values.put(KEY_TASK_DESCRIPTION, item.getDescription());
      values.put(KEY_TASK_DATE, item.getDueDate());
      values.put(KEY_TASK_PRIORITY, item.getPriority());
      values.put(KEY_TASK_STATUS, item.getStatus());
      values.put(KEY_TASK_TIME, item.getTime());
      values.put(KEY_TASK_TAG, item.getTag());

      int result = db.update(TABLE_TASKS, values, KEY_TASK_ID + " = ?",
          new String[] { String.valueOf(item.getId()) });
      if (result > 0) {
        db.setTransactionSuccessful();
        callback.onSuccess();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      callback.onError();
    } finally {
      db.endTransaction();
    }
  }

  public ArrayList<String> getAllItemDate() {
    ArrayList<String> dates = new ArrayList<>();
    SQLiteDatabase db = getReadableDatabase();

    String SQL_QUERY_TASKS_BY_DATE =
        String.format("SELECT %s FROM %s ORDER BY %s ASC", KEY_TASK_DATE, TABLE_TASKS,
            KEY_TASK_DATE);

    Cursor cursor = null;
    try {
      cursor = db.rawQuery(SQL_QUERY_TASKS_BY_DATE, null);
      if (cursor.moveToFirst()) {
        do {
          String date = cursor.getString(cursor.getColumnIndex(KEY_TASK_DATE));
          if (!dates.contains(date)) dates.add(date);
        } while (cursor.moveToNext());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
    }
    return dates;
  }

  @Override public Task getTask(int id) {
    return getItemById(id);
  }

  @Override public ArrayList<Task> getTasks(String date, int taskStatus) {
    return getAllItemByDate(date,taskStatus);
  }

  @Override public void addTask(Task task, TaskRepository.RepositoryResponse callback) {
    addItem(task, callback);
  }

  @Override public void updateTask(Task task, TaskRepository.RepositoryResponse callback) {
    updateItem(task, callback);
  }

  @Override public void removeTask(int id, TaskRepository.RepositoryResponse callback) {
    deleteItemById(id, callback);
  }

  @Override
  public void removeTasks(ArrayList<Integer> ids, TaskRepository.RepositoryResponse callback) {
    deleteItemsById(ids, callback);
  }
}
