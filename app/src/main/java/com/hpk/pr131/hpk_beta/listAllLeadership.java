package com.hpk.pr131.hpk_beta;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.hpk.pr131.hpk_beta.Adapter.LeaderAdapter;
import com.hpk.pr131.hpk_beta.Object.Leader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class listAllLeadership extends AppCompatActivity {

    private List<Leader> list = new ArrayList<>();
    private ProgressDialog progressDialog;
    private LeaderAdapter adapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_leadership);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLeadership);
        setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.leaderList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_teachers__college, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshLeadership:
                progressDialog = new ProgressDialog(this);
                new ParseLeadership().execute();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ParseLeadership extends AsyncTask<Void, Integer, Void>{

        private Elements elementParse;
        private ArrayList<String> listOfName = new ArrayList<String>();
        private ArrayList<String> listOfPosition = new ArrayList<String>();
        private ArrayList<String> listOfWork = new ArrayList<String>();

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
            try{
                doc = Jsoup.connect(Constants.URL_LEADERSHIP).get();
                list.clear();
                listOfName.clear();
                listOfPosition.clear();
                listOfWork.clear();
                elementParse = doc.select(".leadership-wrapper h1");

                for(Element element : elementParse){
                    listOfName.add(element.text());
                }
                publishProgress(0, elementParse.size());

                for(String str : listOfName){
                    String[] words = str.split(" ");
                    String position ="";
                    for(int i=0; i<words.length; i++) {
                        if (i > 2)
                            position += words[i] + " ";
                    }
                    listOfName.set(listOfPosition.size(), words[0]+" "+words[1]+" "+words[2]);
                    listOfPosition.add(position);
                }

                elementParse = doc.select(".leadership-position");
                for(Element element : elementParse){
                    listOfWork.add(element.text());
                }

                for(int i=0; i<listOfName.size(); i++)
                    list.add(new Leader(listOfName.get(i), listOfPosition.get(i), listOfWork.get(i)));

            }catch (IOException e){ e.printStackTrace(); }


            return null;
        }
    }

}
