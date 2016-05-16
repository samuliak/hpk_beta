package com.hpk.pr131.hpk_beta.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hpk.pr131.hpk_beta.Model.NewsModel;
import com.hpk.pr131.hpk_beta.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<NewsModel>  list;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, date;
        public ViewHolder(View v) {
            super(v);
            img = (ImageView) v.findViewById(R.id.news_btm);
            title = (TextView) v.findViewById(R.id.news_title);
            date = (TextView) v.findViewById(R.id.news_date);
        }
    }

    public NewsAdapter(List<NewsModel> myDataset) {
        this.list = myDataset;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        holder.img.setImageBitmap(list.get(position).getPhoto());
        holder.title.setText(list.get(position).getTitle());
        holder.date.setText(list.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}