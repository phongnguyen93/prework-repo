package vn.com.phongnguyen93.justdoit.ui_view;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import vn.com.phongnguyen93.justdoit.utilities.Utilities;

/**
 * Created by phongnguyen on 9/10/16.
 */
public class SnappyRecyclerView extends RecyclerView {

  private Context context;

  public SnappyRecyclerView(Context context) {
    super(context);
  }

  public SnappyRecyclerView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public SnappyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override public boolean fling(int velocityX, int velocityY) {
    try {
      LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
      int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
      int myWidth = Utilities.getScreenWidth(context);

      // views on the screen
      int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
      View lastView = linearLayoutManager.findViewByPosition(lastVisibleItemPosition);
      int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
      View firstView = linearLayoutManager.findViewByPosition(firstVisibleItemPosition);

      // distance we need to scroll
      int leftMargin = (screenWidth - lastView.getWidth()) / 2;
      int rightMargin = (screenWidth - firstView.getWidth()) / 2 + firstView.getWidth();
      int leftEdge = lastView.getLeft();
      int rightEdge = firstView.getRight();
      int scrollDistanceLeft = leftEdge - leftMargin;
      int scrollDistanceRight = rightMargin - rightEdge;

      if (Math.abs(velocityX) < 30) {
        // The fling is slow -> stay at the current page if we are less than half through,
        // or go to the next page if more than half through

        if (leftEdge > screenWidth / 2) {
          // go to next page
          smoothScrollBy(-scrollDistanceRight, 0);
        } else if (rightEdge < screenWidth / 2) {
          // go to next page
          smoothScrollBy(scrollDistanceLeft, 0);
        } else {
          // stay at current page
          if (velocityX > 0) {
            smoothScrollBy(-scrollDistanceRight, 0);
          } else {
            smoothScrollBy(scrollDistanceLeft, 0);
          }
        }
        return true;
      } else {
        // The fling is fast -> go to next page
        if (velocityX > 30) {
          smoothScrollBy(scrollDistanceLeft, 0);
        } else {
          smoothScrollBy(-scrollDistanceRight, 0);
        }
        return true;
      }
    }catch (Exception ex){
      ex.printStackTrace();
    }
    return false;
  }
}