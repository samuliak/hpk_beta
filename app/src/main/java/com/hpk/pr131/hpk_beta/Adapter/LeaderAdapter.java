package com.hpk.pr131.hpk_beta.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hpk.pr131.hpk_beta.Object.Leader;
import com.hpk.pr131.hpk_beta.R;

import java.util.ArrayList;
import java.util.List;

public class LeaderAdapter extends BaseAdapter implements View.OnClickListener{
    private List<Leader> list = new ArrayList<>();
    private Context context;

    public LeaderAdapter(Context context, List<Leader> list){
        this.context = context;
        if (list != null)
            this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (view == null) {
            view = inflater.inflate(R.layout.leader, parent, false);
        }

        TextView lName = (TextView) view.findViewById(R.id.leaderName);
        TextView lPosition = (TextView) view.findViewById(R.id.leaderPosition);
        TextView lWork = (TextView) view.findViewById(R.id.leaderWork);

        lName.setText(list.get(position).getName());
        lPosition.setText(list.get(position).getPosition());
        lWork.setText(list.get(position).getWork());
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Log.e("samuliak", "Click in obj");
    }
}
