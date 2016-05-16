package com.hpk.pr131.hpk_beta.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.hpk.pr131.hpk_beta.Adapter.NewsAdapter;
import com.hpk.pr131.hpk_beta.Constants;
import com.hpk.pr131.hpk_beta.Model.NewsModel;
import com.hpk.pr131.hpk_beta.R;
import com.hpk.pr131.hpk_beta.RecyclerClickListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private volatile List<NewsModel> news_list = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ParseNews().execute();
            }
        });
        try {
            FileInputStream fis = openFileInput(Constants.FILE_NEWS);
            ObjectInputStream is = new ObjectInputStream(fis);
            news_list = (List<NewsModel>) is.readObject();
            is.close();
            fis.close();
            initList();
        } catch (IOException ignored) {} catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class ParseNews extends AsyncTask<Void, String, Void>{
        private Elements elementParse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NewsActivity.this);
            progressDialog.setMessage("Оновлення даних ...");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
            progressDialog = null;
            try {
                FileOutputStream fos = openFileOutput(Constants.FILE_NEWS, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                Log.e("samuliak", "Початок запису. Розмір списку: " + news_list.size());
                os.writeObject(news_list);
                os.flush();
                Log.e("samuliak", "Кінець запису");
                os.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            initList();
        }


        @Override
        protected Void doInBackground(Void... params) {
            Document doc;
            NewsModel model;
            news_list.clear();
            try{
                doc = Jsoup.connect(Constants.URL_NEWS).get();
                elementParse = doc.select(".page-title a");
                // Title and URL added
                for(Element element : elementParse){
                    model = new NewsModel();
                    model.setTitle(element.text());
                    model.setURL(element.attr("href"));
                    news_list.add(model);
                }
                // Date add
                elementParse = doc.select(".news-meta-date time");
                int count = 0;
                Log.e("samuliak", "elementparse.size > "+elementParse.size());
                for(Element element : elementParse){
                    model = news_list.get(count);
                    model.setDate(element.text());
                    news_list.set(count, model);
                    count++;
                }

                elementParse = doc.select(".news-summary p");
                // short_info add
               count = 0;
                for(Element element : elementParse){
                    model = news_list.get(count);
                    model.setShort_Info(element.text());
                    news_list.set(count, model);
                    count++;
                }

                elementParse = doc.select(".news-featured-image img");
                Bitmap btm;
                count = 0;
                // bitmap add
                for(Element element : elementParse){
                    model = news_list.get(count);
                    InputStream inp = new java.net.URL(element.attr("src")).openStream();
                    btm = BitmapFactory.decodeStream(inp);
                    model.setPhoto(btm);
                    model.setURL_PHOTO(element.attr("src"));
                    news_list.set(count, model);
                    count++;
                }

                // long_info add
                for(NewsModel m : news_list) {
                    doc = Jsoup.connect(m.getURL()).get();
                    elementParse = doc.select(".news-body p");
                    String str = "";
                    for(Element e : elementParse){
                        str += e.text();
                    }
                    m.setLong_Info(str);
                }

            }catch (IOException e){}
            return null;
        }
    }

    private void initList() {
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_news);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        assert rv != null;
        rv.setHasFixedSize(true);
        rv.setAdapter(new NewsAdapter(news_list));
        rv.setLayoutManager(llm);
        rv.addOnItemTouchListener(
                new RecyclerClickListener(this, new RecyclerClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(NewsActivity.this, DetailNews.class);
                        intent.putExtra("OBJ_ID",position);
                        startActivity(intent);
                    }
                }));
    }
}
