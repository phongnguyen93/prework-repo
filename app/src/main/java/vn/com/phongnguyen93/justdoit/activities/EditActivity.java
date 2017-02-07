package vn.com.phongnguyen93.justdoit.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import vn.com.phongnguyen93.justdoit.R;
import vn.com.phongnguyen93.justdoit.ui_view.TagPickerDialog;
import vn.com.phongnguyen93.justdoit.model.Task;
import vn.com.phongnguyen93.justdoit.repository.TaskRepository;
import vn.com.phongnguyen93.justdoit.utilities.Utilities;

/**
 * Created by phongnguyen on 2/4/17.
 */

public class EditActivity extends AppCompatActivity
    implements TaskRepository.RepositoryResponse, TagPickerDialog.DialogCallback {
  public static final String TAG = EditActivity.class.getSimpleName();
  private EditText edtLabel;
  private EditText edtDesc;
  private EditText edtDate;
  private EditText edtTime;
  private ImageView imgTagPicker;
  private int currentPriority = 0;
  private int currentTagId;

  private DatePickerDialog datePickerDialog;
  private TimePickerDialog timePickerDialog;

  private SimpleDateFormat dateFormatter;

  private Task editTask;
  private ImageView imgRight;
  private ImageView imgLeft;
  private SwitchCompat switchStatus;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    currentTagId = R.drawable.ic_workplace;
    setContentView(R.layout.fragment_task_edit);
    setSupportActionBar((Toolbar) findViewById(R.id.MyToolbar));

    TagPickerDialog.setDialogCallback(this);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    if (getIntent() != null) {
      editTask = getIntent().getParcelableExtra("task");
    }

    final TextView tvPriority = (TextView) findViewById(R.id.tv_priority);
    tvPriority.setText(Html.fromHtml(getPriorityString(currentPriority)));

    imgLeft = (ImageView) findViewById(R.id.img_left);
    imgRight = (ImageView) findViewById(R.id.img_right);
    imgLeft.setAlpha(0.3f);
    imgLeft.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (currentPriority > 0) {
          tvPriority.setText(Html.fromHtml(getPriorityString(--currentPriority)));
          Log.d("priority", currentPriority + "");
        }

        handlePriorityChange(currentPriority);
      }
    });

    imgRight.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (currentPriority < 2) {
          tvPriority.setText(Html.fromHtml(getPriorityString(++currentPriority)));
          Log.d("priority", currentPriority + "");
        }
        handlePriorityChange(currentPriority);
      }
    });

    switchStatus = (SwitchCompat) findViewById(R.id.switch_status);

    imgTagPicker = (ImageView) findViewById(R.id.img_tag_pick);
    imgTagPicker.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
    imgTagPicker.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        TagPickerDialog.newInstance().show(getSupportFragmentManager(), TagPickerDialog.TAG);
      }
    });


    Calendar newCalendar = Calendar.getInstance();
    datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, monthOfYear, dayOfMonth);
        edtDate.setText(Utilities.getDate(newDate.getTime()));
      }
    }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
        newCalendar.get(Calendar.DAY_OF_MONTH));

    timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
      @Override public void onTimeSet(TimePicker timePicker, int i, int i1) {
        edtTime.setText(String.format("%s:%s ", String.valueOf(i),
            String.valueOf(i1).length() < 2 ? "0" + String.valueOf(i1) : String.valueOf(i1)));
      }
    }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);

    edtLabel = (EditText) findViewById(R.id.edt_label);
    edtDesc = (EditText) findViewById(R.id.edt_description);
    edtDate = (EditText) findViewById(R.id.edt_date);
    edtTime = (EditText) findViewById(R.id.edt_time);

    if (editTask != null) {
      edtLabel.setText(editTask.getLabel());
      edtDesc.setText(editTask.getDescription());
      edtDate.setText(editTask.getDueDate());
      edtTime.setText(editTask.getTime());
      currentPriority = editTask.getPriority();
      tvPriority.setText(Html.fromHtml(getPriorityString(currentPriority)));

      if (editTask.getStatus() == Task.TASK_DONE) {
        switchStatus.setChecked(true);
      } else {
        switchStatus.setChecked(false);
      }

      currentTagId = editTask.getTag();
      imgTagPicker.setBackgroundColor(Color.WHITE);
      imgTagPicker.setImageDrawable(ContextCompat.getDrawable(this, currentTagId));
    }

    edtDate.setInputType(InputType.TYPE_NULL);
    edtDate.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        datePickerDialog.show();
      }
    });

    edtTime.setInputType(InputType.TYPE_NULL);
    edtTime.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        timePickerDialog.show();
      }
    });

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
          task.setPriority(currentPriority);

          if (switchStatus.isChecked()) {
            task.setStatus(Task.TASK_DONE);
          } else {
            task.setStatus(Task.TASK_TODO);
          }

          if (currentTagId != 0) task.setTag(currentTagId);

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

  private void handlePriorityChange(int currentPriority) {
    if (currentPriority == 2) {
      imgRight.setAlpha(0.3f);
    } else {
      imgRight.setAlpha(1.0f);
    }

    if (currentPriority == 0) {
      imgLeft.setAlpha(0.3f);
    } else {
      imgLeft.setAlpha(1.0f);
    }
  }

  private String getPriorityString(int currentPriority) {
    String s = "";
    switch (currentPriority) {
      case 0:
        s = getString(R.string.high_priority);
        break;
      case 1:
        s = getString(R.string.medium_priority);
        break;
      case 2:
        s = getString(R.string.low_priority);
        break;
    }
    return s;
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

  @Override public void dialogCallback(int tagId) {
    currentTagId = tagId;
    imgTagPicker.setBackgroundColor(Color.WHITE);
    imgTagPicker.setImageDrawable(ContextCompat.getDrawable(this, tagId));
  }
}
