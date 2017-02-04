package vn.com.phongnguyen93.justdoit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by phongnguyen on 2/4/17.
 */

public class EditActivity extends AppCompatActivity implements TaskRepository.RepositoryResponse {
  public static final String TAG = EditActivity.class.getSimpleName();
  private EditText edtLabel;
  private EditText edtDesc;
  private EditText edtDate;
  private EditText edtTime;

  private Task editTask;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_task_edit);
    setSupportActionBar((Toolbar) findViewById(R.id.MyToolbar));
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowTitleEnabled(false);
      getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow);
    }

    if (getIntent() != null) {
      editTask = getIntent().getParcelableExtra("task");
    }

    edtLabel = (EditText) findViewById(R.id.edt_label);
    edtDesc = (EditText) findViewById(R.id.edt_description);
    edtDate = (EditText) findViewById(R.id.edt_date);
    edtTime = (EditText) findViewById(R.id.edt_time);

    if (editTask != null) {
      edtLabel.setText(editTask.getLabel());
      edtDesc.setText(editTask.getDescription());
      edtDate.setText(editTask.getDueDate());
      edtTime.setText(editTask.getTime());
    }

    Button btnDone = (Button) findViewById(R.id.btn_done);
    btnDone.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (!TextUtils.isEmpty(edtLabel.getText().toString()) && !TextUtils.isEmpty(
            edtDate.getText().toString()) && !TextUtils.isEmpty(edtTime.getText().toString())) {

          Task task = new Task();
          task.setLabel(edtLabel.getText().toString());
          task.setDescription(edtDesc.getText().toString());
          task.setDueDate(edtDate.getText().toString());
          task.setTime(edtTime.getText().toString());
          task.setPriority(1);
          task.setStatus(0);

          if (editTask == null) {
            TaskRepository.getsInstance(EditActivity.this).addTask(task, EditActivity.this);
          } else {
            task.setId(editTask.getId());
            TaskRepository.getsInstance(EditActivity.this).updateTask(task, EditActivity.this);
          }
        }
      }
    });
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      this.finish();
    }
    return true;
  }

  @Override public void onSuccess() {
    this.finish();
  }

  @Override public void onError() {

  }
}
