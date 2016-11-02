package com.linked.erfli.moduleb.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linked.erfli.moduleb.R;

import java.util.List;

/**
 * Created by erfli on 6/15/16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private final int mBackground;
    private List<Story> mDataset;

    private final TypedValue mTypedValue = new TypedValue();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView newsTitleTV;
        public ImageView newsIV;

        public ViewHolder(View v) {
            super(v);
            newsTitleTV = (TextView) v.findViewById(R.id.news_title);
            newsIV = (ImageView) v.findViewById(R.id.news_image);
        }
    }

    public NewsAdapter(Context context, List<Story> myDataset) {
        this.mDataset = myDataset;
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        this.context = context;
        mBackground = mTypedValue.resourceId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.module2_item_zhihu, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Story story = mDataset.get(position);
        holder.newsTitleTV.setText(mDataset.get(position).getTitle());
        holder.newsTitleTV.setTextColor(context.getResources().getColor(R.color.primary_text));
        Glide.clear(holder.newsIV);
        Glide.with(holder.newsIV.getContext())
                .load(story.getImages().get(0))
                .fitCenter()
                .into(holder.newsIV);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public Story getItem(int position) {
        return mDataset.get(position);
    }

    public void updateData(List<Story> stories) {
        mDataset.clear();
        mDataset.addAll(stories);
        notifyDataSetChanged();
    }

    public void addData(List<Story> stories) {
        mDataset.addAll(stories);
        notifyDataSetChanged();
    }
}
