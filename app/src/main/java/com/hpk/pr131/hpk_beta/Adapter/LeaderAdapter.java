package com.hpk.pr131.hpk_beta.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hpk.pr131.hpk_beta.Activity.DetailLeaderActivity;
import com.hpk.pr131.hpk_beta.Constants;
import com.hpk.pr131.hpk_beta.Model.LeaderModel;
import com.hpk.pr131.hpk_beta.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class LeaderAdapter extends BaseAdapter implements View.OnClickListener{
    private List<LeaderModel> list = new ArrayList<>();
    private Context context;
    private Typeface typeface;

    public LeaderAdapter(Context context, List<LeaderModel> list){
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

        typeface = Typeface.createFromAsset(view.getContext().getAssets(), Constants.fontText);
        lName.setTypeface(typeface);
        lName.setText(list.get(position).getName());

        typeface = Typeface.createFromAsset(view.getContext().getAssets(), Constants.fontExample);
        lPosition.setTypeface(typeface);
        lPosition.setText(list.get(position).getPosition());

        typeface = Typeface.createFromAsset(view.getContext().getAssets(), Constants.fontTitle);
        lWork.setTypeface(typeface);
        lWork.setText(list.get(position).getWork());

        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, DetailLeaderActivity.class);
        TextView name = (TextView) v.findViewById(R.id.leaderName);
        for(int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name.getText())){
                try {
                    intent.putExtra("OBJ_id", i);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                    break;
                }catch (Exception e) {e.printStackTrace();}
            }
        }
    }
}