package com.hpk.pr131.hpk_beta.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.app.DialogFragment;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hpk.pr131.hpk_beta.R;
import com.hpk.pr131.hpk_beta.SendFragment;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        MultiDex.install(this);
        Button sendBtn = (Button) findViewById(R.id.sendBtn);
        assert sendBtn != null;
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment sendFragment = new SendFragment();
                sendFragment.show(getFragmentManager(), "TAG");
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onClickDirect(View view) {
        String number = "tel:0382630505";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void onClickZamNavch(View view)
    {
        String number = "tel:0382632272";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void onClickZamVux(View view) {
        String number = "tel:0382631453";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void onClickZamVur(View view) {
        String number = "tel:0382631598";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void onClickZamAdm(View view) {
        String number = "tel:0382631598";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void onClickZavKS(View view)
    {
        String number = "tel:0382631167";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void onClickZavEP(View view) {
        String number = "tel:0382630691";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void onClickZavPR(View view) {
        String number = "tel:0382631167";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void onClickZavVR(View view) {
        String number = "tel:0382631230";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void onClickZavZao(View view) {
        String number = "tel:0382631230";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void onClickBugh(View view) {
        String number = "tel:0382631760";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void onClickLib(View view) {
        String number = "tel:0382631453";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void onClickNav(View view) {
        String number = "tel:0382630691";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void onClickCher(View view) {
        String number = "tel:0382630691";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        startActivity(callIntent);
    }
}
