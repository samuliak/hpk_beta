package com.hpk.pr131.hpk_beta.Activity;

import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hpk.pr131.hpk_beta.Constants;
import com.hpk.pr131.hpk_beta.Model.NewsModel;
import com.hpk.pr131.hpk_beta.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public class DetailNews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        MultiDex.install(this);
        int position = (int) getIntent().getExtras().get("OBJ_ID");
        TextView title = (TextView) findViewById(R.id.d_title);
        TextView textView = (TextView) findViewById(R.id.d_info);
        ImageViewTouch im = (ImageViewTouch) findViewById(R.id.d_img);
        assert textView != null;
        NewsModel news = null;
        try {
            FileInputStream fis = openFileInput(Constants.FILE_NEWS);
            ObjectInputStream is = new ObjectInputStream(fis);
            news = ((List<NewsModel>) is.readObject()).get(position);
            is.close();
            fis.close();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert im != null;
        assert title != null;
        assert news != null;
        im.setImageDrawable(new BitmapDrawable(getResources(), news.getPhoto()));

        Typeface typeface = Typeface.createFromAsset(getAssets(), Constants.fontText);
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), Constants.fontOneDay);

        textView.setTypeface(typeface);
        title.setTypeface(typeface2);
        title.setText(news.getTitle());
        textView.setText(news.getLong_Info());
    }
}

