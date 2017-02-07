package vn.com.phongnguyen93.justdoit.repository;

import java.util.ArrayList;
import vn.com.phongnguyen93.justdoit.model.Task;

/**
 *
 * Created by phongnguyen on 2/4/17.
 */

public interface RepositoryInterface {

  Task getTask(int id);

  ArrayList<Task> getTasks(String date, int taskStatus);

  void addTask(Task task,TaskRepository.RepositoryResponse callback);

  void updateTask(Task task,TaskRepository.RepositoryResponse callback);

  void removeTask(int id,TaskRepository.RepositoryResponse callback);

  void removeTasks(ArrayList<Integer> ids,TaskRepository.RepositoryResponse callback);
}
