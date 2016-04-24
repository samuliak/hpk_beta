package com.hpk.pr131.hpk_beta.Activity;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.hpk.pr131.hpk_beta.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class DetailNews extends AppCompatActivity {

    public Drawable dr = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        String info = getIntent().getStringExtra("OBJ_INFO");
        String URL = getIntent().getStringExtra("OBJ_URL");
        Log.e("samuliak", "URL: "+URL);
        TextView textView = (TextView) findViewById(R.id.d_info);
        assert textView != null;
        textView.setText(info);

        getDrawable r =  new getDrawable();
        r.execute(URL);
        try {
            dr = r.get();
            ImageView im = (ImageView) findViewById(R.id.d_img);
            assert im != null;
            im.setImageDrawable(dr);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}

class getDrawable extends AsyncTask<String, Void, Drawable> {

    @Override
    protected Drawable doInBackground(String... params) {
        Drawable dr = null;
        try {
            dr = Drawable.createFromStream(
                    (InputStream) new URL(params[0]).getContent(), "src");
        } catch (IOException e) {
            e.printStackTrace();
        };
        return dr;
    }
}
