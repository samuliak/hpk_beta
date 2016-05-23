package com.hpk.pr131.hpk_beta.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.hpk.pr131.hpk_beta.Adapter.LeaderAdapter;
import com.hpk.pr131.hpk_beta.Constants;
import com.hpk.pr131.hpk_beta.Model.LeaderModel;
import com.hpk.pr131.hpk_beta.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListAllLeadershipActivity extends AppCompatActivity {

    private List<LeaderModel> list = new ArrayList<>();
    private ProgressDialog progressDialog;
    private LeaderAdapter adapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_leadership);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLeadership);
        setSupportActionBar(toolbar);
        MultiDex.install(this);
        lv = (ListView) findViewById(R.id.leaderList);
        try {
            Log.e("samuliak", "Start reading leadership");
            FileInputStream fis = openFileInput(Constants.FILE_LEADERSHIP);
            ObjectInputStream is = new ObjectInputStream(fis);
            Log.e("samuliak", "Start reading list");
            list = (ArrayList<LeaderModel>) is.readObject();
            Log.e("samuliak", "the reading is ok");
            is.close();
            fis.close();
            adapter = new LeaderAdapter(getApplicationContext(), list);
            lv.setAdapter(adapter);
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

    private boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshActivity:
                if ( !isOnline() ){
                    Toast.makeText(getApplicationContext(),
                            "Немає з'єднання з мережею Інтернет!",Toast.LENGTH_LONG).show();
                }else {
                    progressDialog = new ProgressDialog(this);
                    new ParseLeadership().execute();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ParseLeadership extends AsyncTask<Void, Integer, Void> {

        private Elements elementParse;
        private ArrayList<String> listOfName = new ArrayList<String>();
        private ArrayList<String> listOfPosition = new ArrayList<String>();
        private ArrayList<String> listOfWork = new ArrayList<String>();
        private ArrayList<String> listOfDetailInfo = new ArrayList<>();
        private ArrayList<Bitmap> listOfPhoto = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Оновлення даних ...");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
            try {
                FileOutputStream fos = openFileOutput(Constants.FILE_LEADERSHIP, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(list);
                os.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            adapter = new LeaderAdapter(getApplicationContext(), list);
            lv.setAdapter(adapter);
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
                doc = Jsoup.connect(Constants.URL_LEADERSHIP).get();
                list.clear();
                listOfName.clear();
                listOfPosition.clear();
                listOfWork.clear();
                listOfPhoto.clear();
                elementParse = doc.select(".leadership-wrapper h1");

                for (Element element : elementParse) {
                    listOfName.add(element.text());
                }
                publishProgress(0, 10);

                for (String str : listOfName) {
                    String[] words = str.split(" ");
                    String position = "";
                    for (int i = 0; i < words.length; i++) {
                        if (i > 2)
                            position += words[i] + " ";
                    }
                    listOfName.set(listOfPosition.size(), words[0] + " " + words[1] + " " + words[2]);
                    listOfPosition.add(position);
                }
                publishProgress(2, 10);

                elementParse = doc.select(".leadership-position");
                for (Element element : elementParse) {
                    listOfWork.add(element.text());
                }
                publishProgress(4, 10);

                elementParse = doc.select(".leadership-photo img");
                for(int i=0; i<elementParse.size(); i++){
                    InputStream inp = new java.net.URL(elementParse.attr("src")).openStream();
                    Bitmap btm = BitmapFactory.decodeStream(inp);
                    listOfPhoto.add(btm);
                }
                Log.e("samuliak", "listofPhoto size > "+listOfPhoto.size());
                publishProgress(6, 10);

                elementParse = doc.select(".leadership-wrapper p");
                for(int i=0; i<elementParse.size(); i++)
                    listOfDetailInfo.add("");
                publishProgress(8, 10);

                int count = 0;
                for (int i = 0; i < elementParse.size(); i++) {
                    if (i<= 4) {
                        Log.e("samuliak", "parse detail info:" + elementParse.get(i).text());
                        listOfDetailInfo.set(count, listOfDetailInfo.get(count) + elementParse.get(i).text() + " ");
                    }
                    else if (i > 11 && i <= 13) {
                        Log.e("samuliak", "parse detail info:" + elementParse.get(i).text());
                            listOfDetailInfo.set(count, listOfDetailInfo.get(count) + elementParse.get(i).text() + " ");
                        }
                    else if (i > 15) {
                        Log.e("samuliak", "parse detail info:" + elementParse.get(i).text());
                        listOfDetailInfo.set(count, listOfDetailInfo.get(count) + elementParse.get(i).text() + " ");
                    }
                    else {
                        count++;
                        Log.e("samuliak", "parse detail info:" + elementParse.get(i).text());
                        listOfDetailInfo.set(count, elementParse.get(i).text());
                    }
                }
                publishProgress(10, 10);
                for (int i = 0; i < listOfName.size(); i++) {
                    LeaderModel leader = new LeaderModel(listOfName.get(i), listOfPosition.get(i), listOfWork.get(i),
                            listOfDetailInfo.get(i));
                    leader.setPhoto(listOfPhoto.get(i));
                    list.add(leader);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
