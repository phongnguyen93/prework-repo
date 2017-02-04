package vn.com.phongnguyen93.justdoit;

import java.util.ArrayList;

/**
 *
 * Created by phongnguyen on 2/4/17.
 */

public interface RepositoryInterface {

  Task getTask(int id);

  ArrayList<Task> getTasks(String date);

  void addTask(Task task,TaskRepository.RepositoryResponse callback);

  void updateTask(Task task,TaskRepository.RepositoryResponse callback);

  void removeTask(int id,TaskRepository.RepositoryResponse callback);

  void removeTasks(ArrayList<Integer> ids,TaskRepository.RepositoryResponse callback);
}
