package com.hpk.pr131.hpk_beta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hpk.pr131.hpk_beta.Model.LeaderModel;

public class DetailLeaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_leader);
        TextView tv = (TextView) findViewById(R.id.detailInfo);
        LeaderModel leader = (LeaderModel) getIntent().getExtras().get("OBJ");
        assert leader != null;
        tv.setText(leader.getdDetailInfo());
    }

}
