package com.hpk.pr131.hpk_beta.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hpk.pr131.hpk_beta.Model.ReplaceModel;
import com.hpk.pr131.hpk_beta.R;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReplacementAdapter extends RecyclerView.Adapter<ReplacementAdapter.ViewHolder> {
    private Map<String, List<ReplaceModel>> mDataset;
    private List<String> listGroup;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public ReplacementAdapter(Map<String, List<ReplaceModel>> myDataset, List<String> list) {
        Log.e("samuliak", "Constructor ReplacementAdapter");
        this.mDataset = myDataset;
        this.listGroup = list;
    }

    @Override
    public ReplacementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.replace_item, parent, false);
//        ViewHolder vh = new ViewHolder(v);
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}