package com.hpk.pr131.hpk_beta.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hpk.pr131.hpk_beta.Model.ReplaceModel;
import com.hpk.pr131.hpk_beta.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ReplacementAdapter extends BaseAdapter {
    private List<ReplaceModel> list = new ArrayList<>();
    private Context context;

    public ReplacementAdapter(List<ReplaceModel> list, Context context) {
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
        if (view == null)
            view = inflater.inflate(R.layout.replacement, parent, false);
        TextView group = (TextView) view.findViewById(R.id.group);
        TextView pair = (TextView) view.findViewById(R.id.pair);
        TextView replaced = (TextView) view.findViewById(R.id.replaced);
        TextView subject = (TextView) view.findViewById(R.id.subject);
        TextView teacher = (TextView) view.findViewById(R.id.teacher);
        TextView audience = (TextView) view.findViewById(R.id.audience);
        group.setText(list.get(position).getGroup());
        pair.setText(list.get(position).getPair());
        replaced.setText(list.get(position).getReplaced());
        subject.setText(list.get(position).getSubject());
        teacher.setText(list.get(position).getTeacher());
        audience.setText(list.get(position).getAudience());
        return view;
    }
}
