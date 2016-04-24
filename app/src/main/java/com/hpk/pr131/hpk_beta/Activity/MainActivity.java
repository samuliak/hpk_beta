package com.hpk.pr131.hpk_beta.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.hpk.pr131.hpk_beta.R;

public class MainActivity extends AppCompatActivity {

    private GoogleApiClient client;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    public void menuListener(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.btnNews:
                i = new Intent(this, NewsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            case R.id.btnGallery:

                break;
            case R.id.btnReplaced:
                i = new Intent(this, ReplacementActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            case R.id.btnTimetable:

                break;
            case R.id.btnHistory:
                i = new Intent(this, HistoryActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            case R.id.btnLeadership:
                i = new Intent(this, ListAllLeadershipActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
        }
    }
}
