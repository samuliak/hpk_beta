package com.hpk.pr131.hpk_beta.Activity;

import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hpk.pr131.hpk_beta.R;

public class ApplicantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicants);
        MultiDex.install(this);
    }
}
