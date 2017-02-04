package vn.com.phongnguyen93.justdoit;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by phongnguyen on 9/4/16.
 */
public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int mVerticalSpaceHeight;

    public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
        this.mVerticalSpaceHeight = mVerticalSpaceHeight;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.left = mVerticalSpaceHeight;
        outRect.right = mVerticalSpaceHeight;
        outRect.top = mVerticalSpaceHeight;
        outRect.bottom = mVerticalSpaceHeight;

    }
}
