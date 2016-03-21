package com.hpk.pr131.hpk_beta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hpk.pr131.hpk_beta.Object.Leader;

import java.util.List;

public class DetailLeaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_leader);
        TextView tv = (TextView) findViewById(R.id.detailInfo);
        Leader leader = (Leader) getIntent().getExtras().get("OBJ");
        tv.setText(leader.getdDetailInfo());
    }

}
