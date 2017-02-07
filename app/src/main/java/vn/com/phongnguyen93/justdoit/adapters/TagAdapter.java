package vn.com.phongnguyen93.justdoit.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import vn.com.phongnguyen93.justdoit.R;

/**
 * Created by phongnguyen on 2/5/17.
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {
  private TagInteractionCallback callback;

  public interface TagInteractionCallback{
    void onTagClick(int tagId);
  }

  private Context context;
  private final int[] tagId = {
      R.drawable.ic_book, R.drawable.ic_breakfast, R.drawable.ic_cleaning, R.drawable.ic_gymnastics,
      R.drawable.ic_workplace, R.drawable.ic_shopping_bag
  };

  public TagAdapter(Context context, TagInteractionCallback callback) {
    this.context = context;
    this.callback = callback;
  }

  @Override public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item_layout, null);
    return new TagViewHolder(v);
  }

  @Override public void onBindViewHolder(TagViewHolder holder, final int position) {
    holder.bindView(tagId[position]);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        callback.onTagClick(tagId[position]);
      }
    });
  }

  @Override public int getItemCount() {
    return tagId.length;
  }

  class TagViewHolder extends RecyclerView.ViewHolder {
    private ImageView imgTag;

    public TagViewHolder(View itemView) {
      super(itemView);
      imgTag = (ImageView) itemView.findViewById(R.id.img_tag);
      imgTag.setColorFilter(ContextCompat.getColor(context,R.color.colorPrimary));
    }

    void bindView(int tagId) {
      imgTag.setImageDrawable(ContextCompat.getDrawable(context, tagId));
    }
  }
}
