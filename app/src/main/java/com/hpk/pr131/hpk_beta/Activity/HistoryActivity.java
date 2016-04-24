package com.hpk.pr131.hpk_beta.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hpk.pr131.hpk_beta.Constants;
import com.hpk.pr131.hpk_beta.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HistoryActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private String History;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHistory);
        setSupportActionBar(toolbar);
        tv = (TextView) findViewById(R.id.historyCollege);
        try {
            FileInputStream fis = openFileInput(Constants.FILE_HISTORY);
            ObjectInputStream is = new ObjectInputStream(fis);
            History = (String) is.readObject();
            is.close();
            fis.close();
            tv.setText(History);
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_leaders__college, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshActivity:
                progressDialog = new ProgressDialog(this);
                new ParseHistory().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ParseHistory extends AsyncTask<Void, Integer, Void> {

        private Elements elementParse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Оновлення даних ...");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
            try {
                FileOutputStream fos = openFileOutput(Constants.FILE_HISTORY, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(History);
                os.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            tv.setText(History);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setMessage("Знайдено " + values[1] + " елементів");
            progressDialog.setProgress((int) ((values[0] / (float) values[1]) * 100));
        }

        @Override
        protected Void doInBackground(Void... params) {
            Document doc;
            try {
                doc = Jsoup.connect(Constants.URL_HISTORY).get();
                History="";
                elementParse = doc.select(".news-body p");
                StringBuilder stringBuilder = new StringBuilder();
                for(int i=0; i < elementParse.size(); i++){
                    stringBuilder.append("\n"+elementParse.get(i).text());
                }
                History = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
