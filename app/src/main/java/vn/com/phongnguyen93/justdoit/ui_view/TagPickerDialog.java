package vn.com.phongnguyen93.justdoit.ui_view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import vn.com.phongnguyen93.justdoit.R;
import vn.com.phongnguyen93.justdoit.utilities.Utilities;
import vn.com.phongnguyen93.justdoit.adapters.TagAdapter;

/**
 * Dialog to input the name for a new folder to create.
 *
 * Triggers the folder creation when name is confirmed.
 */
public class TagPickerDialog extends DialogFragment implements TagAdapter.TagInteractionCallback {

  public static final String TAG = TagPickerDialog.class.getSimpleName();

  private static DialogCallback mCallback;

  @Override public void onTagClick(int tagId) {
    mCallback.dialogCallback(tagId);
    getDialog().dismiss();
  }

  public interface DialogCallback {
    void dialogCallback(int tagId);
  }

  public static void setDialogCallback(DialogCallback callback) {
    mCallback = callback;
  }

  public static TagPickerDialog newInstance() {
    TagPickerDialog frag = new TagPickerDialog();
    Bundle args = new Bundle();
    frag.setArguments(args);
    return frag;
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Inflate the layout for the dialog
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View v = inflater.inflate(R.layout.tag_picker_layout, null);
    v.setBackground(Utilities.generateGradientDrawable(new int[] {
        ContextCompat.getColor(getContext(), R.color.colorPrimary),
        ContextCompat.getColor(getContext(), R.color.colorAccent)
    }));
    SnappyRecyclerView rvTaskTag = (SnappyRecyclerView)v.findViewById(R.id.rv_task_tag);
    rvTaskTag.setLayoutManager( new ScrollingLinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false,
        10000));
    VerticalSpaceItemDecoration spaceItemDecoration = new VerticalSpaceItemDecoration(16);
    rvTaskTag.addItemDecoration(spaceItemDecoration);
    TagAdapter tagAdapter = new TagAdapter(getContext(),this);
    rvTaskTag.setAdapter(tagAdapter);

    // Build the dialog
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setView(v);
    Dialog d = builder.create();
    return d;
  }


}
