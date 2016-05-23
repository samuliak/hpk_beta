package com.hpk.pr131.hpk_beta.Activity;

import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.hpk.pr131.hpk_beta.Constants;
import com.hpk.pr131.hpk_beta.Model.LeaderModel;
import com.hpk.pr131.hpk_beta.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class DetailLeaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_leader);
            MultiDex.install(this);
            TextView tvName = (TextView) findViewById(R.id.leaderNameDetail);
            TextView tvPosition = (TextView) findViewById(R.id.leaderPositionDetail);
            TextView tvInfo = (TextView) findViewById(R.id.detailInfo);
            ImageView photo = (ImageView) findViewById(R.id.leaderPhoto);
            assert tvInfo != null;
            assert tvName != null;
            assert tvPosition != null;
            assert photo != null;
            LeaderModel leader = null;
            int i = (int) getIntent().getExtras().get("OBJ_id");
            Log.e("samuliak","i > "+i);
            try {
                FileInputStream fis = openFileInput(Constants.FILE_LEADERSHIP);
                ObjectInputStream is = new ObjectInputStream(fis);
                leader = ((ArrayList<LeaderModel>) is.readObject()).get(i);
                is.close();
                fis.close();
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            assert leader != null;

            tvName.setText(leader.getName());
            tvPosition.setText(leader.getPosition());
            tvInfo.setText(leader.getDetailInfo());
            setLeaderPhoto(photo, i);

            Typeface typeface = Typeface.createFromAsset(getAssets(), Constants.fontText);
            tvName.setTypeface(typeface);
            tvPosition.setTypeface(typeface);

            typeface = Typeface.createFromAsset(getAssets(), Constants.fontExample);
            tvInfo.setTypeface(typeface);

        }catch (Exception e){e.printStackTrace();}
    }

    private void setLeaderPhoto(ImageView photo, int i) {
        switch (i){
            case 0:
                photo.setImageResource(R.drawable.ph0);
                break;
            case 1:
                photo.setImageResource(R.drawable.ph1);
                break;
            case 2:
                photo.setImageResource(R.drawable.ph2);
                break;
            case 3:
                photo.setImageResource(R.drawable.ph3);
                break;
            case 4:
                photo.setImageResource(R.drawable.ph4);
                break;
            case 5:
                photo.setImageResource(R.drawable.ph5);
                break;
            case 6:
                photo.setImageResource(R.drawable.ph6);
                break;
            case 7:
                photo.setImageResource(R.drawable.ph7);
                break;
            case 8:
                photo.setImageResource(R.drawable.ph8);
                break;
            case 9:
                photo.setImageResource(R.drawable.ph9);
                break;
        }
    }

}
