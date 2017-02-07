package vn.com.phongnguyen93.justdoit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import vn.com.phongnguyen93.justdoit.R;
import vn.com.phongnguyen93.justdoit.utilities.Utilities;
import vn.com.phongnguyen93.justdoit.activities.EditActivity;

/**
 * Created by phongnguyen on 2/7/17.
 */

public class EmptyFragment extends Fragment {

  public static EmptyFragment newInstance() {
    Bundle args = new Bundle();
    EmptyFragment fragment = new EmptyFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_task_empty,container,false);

    Button createTask = (Button)v.findViewById(R.id.btn_start);

    createTask.setBackground(Utilities.generateGradientDrawable(new int[] {
        ContextCompat.getColor(getContext(), R.color.colorPrimary),
        ContextCompat.getColor(getContext(), R.color.colorAccent)
    }));
    createTask.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(new Intent(getContext(),EditActivity.class));
      }
    });

    return v;
  }
}
