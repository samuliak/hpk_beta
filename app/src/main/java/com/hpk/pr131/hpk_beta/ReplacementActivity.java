package com.hpk.pr131.hpk_beta;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.hpk.pr131.hpk_beta.Adapter.ReplacementAdapter;
import com.hpk.pr131.hpk_beta.Model.ReplaceModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReplacementActivity extends AppCompatActivity {
    private List<ReplaceModel> list = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("samuliak", "Click!");
                progressDialog = new ProgressDialog(ReplacementActivity.this);
                new ParseReplacement().execute();
            }
        });
        try {
            FileInputStream fis = openFileInput(Constants.FILE_REPLACEMENT);
            ObjectInputStream is = new ObjectInputStream(fis);
            list = (List<ReplaceModel>) is.readObject();
            is.close();
            fis.close();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class ParseReplacement extends AsyncTask<Void, Integer, Void> {

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
                FileOutputStream fos = openFileOutput(Constants.FILE_REPLACEMENT, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(list);
                Log.e("samuliak", "onPostExecute. Write list");
                os.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Нова реалізація замін (!) за допомогою таблиць

            TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
            Log.e("samuliak", "List size : " + list.size());
            LayoutInflater inflater = LayoutInflater.from(ReplacementActivity.this);
            for (int i = 0; i < list.size(); i++) {
                TableRow tableRow = (TableRow) inflater.inflate(R.layout.table_row, null);

                TextView textView = (TextView) tableRow.findViewById(R.id.groupRow);
                if (list.get(i).getGroup() != null) {
                    if (list.get(i).getGroup().trim().length() < 20)
                        textView.setText(list.get(i).getGroup());
                    else
                        textView.setText(list.get(i).getGroup().substring(0, 20));
                }

                textView = (TextView) tableRow.findViewById(R.id.pairRow);
                    if (list.get(i).getPair() != null) {
                    if (list.get(i).getPair().length() < 20)
                        textView.setText(list.get(i).getPair());
                    else
                        textView.setText(list.get(i).getPair().substring(0, 20));
                }

                textView = (TextView) tableRow.findViewById(R.id.replacedRow);
                if (list.get(i).getReplaced() != null) {
                    if (list.get(i).getReplaced().length() < 20)
                        textView.setText(list.get(i).getReplaced());
                    else
                        textView.setText(list.get(i).getReplaced().substring(0, 17));
                }

                textView = (TextView) tableRow.findViewById(R.id.subjectRow);
                if (list.get(i).getSubject() != null){
                    if (list.get(i).getSubject().length() < 20)
                        textView.setText(list.get(i).getSubject());
                    else
                        textView.setText(list.get(i).getSubject().substring(0, 20));
                }

                textView = (TextView) tableRow.findViewById(R.id.teacherRow);
                if (list.get(i).getTeacher() != null) {
                    if (list.get(i).getTeacher().length() < 20)
                        textView.setText(list.get(i).getTeacher());
                    else
                        textView.setText(list.get(i).getTeacher().substring(0, 20));
                }

                textView = (TextView) tableRow.findViewById(R.id.audienceRow);
                if (list.get(i).getAudience() != null) {
                    if (list.get(i).getAudience().length() < 20)
                        textView.setText(list.get(i).getAudience());
                    else
                        textView.setText(list.get(i).getAudience().substring(0, 20));
                }

                tableLayout.addView(tableRow);

            }

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
            list.clear();
            Log.e("samuliak", "doInBackground start");
            try {
                doc = Jsoup.connect(Constants.URL_REPLACEMENT).get();
                elementParse = doc.select(".news-body td");
                Log.e("samuliak","size elementParse >> "+elementParse.size());
                int k=0;
                ReplaceModel model;
                String[] str = new String[6];
                for(int i=0; i<elementParse.size(); i++){
                    if (elementParse.get(i).text().length() < 4 && elementParse.get(i-1).text().length() < 5 && k == 0){
                        if (elementParse.get(i-6).text().length() < 5){
                            str[k] = elementParse.get(i-11).text();
                            k++;
                        } else {
                            str[k] = elementParse.get(i - 6).text();
                            k++;
                        }
                    } else {
                        if (elementParse.get(i).text().length() < 2 && elementParse.get(i+1).text().length() > 20 && k == 1){
                            str[k] = "";
                            k++;
                            str[k] = elementParse.get(i+1).text();
                            k = 8;
                            if (elementParse.get(i+2).text().length() < 2 && elementParse.get(i+3).text().length() > 5){
                                i = i+2;
                            }
                        }
                    }
                    if (k <= 6) {
                        str[k] = elementParse.get(i).text();
                        k++;
                    }

                    if (k > 5) {
                        model = new ReplaceModel(str[0], str[1], str[2], str[3], str[4], str[5]);
                        Log.e("samuliak", "Info:  " + model.toString());
                        list.add(model);
                        str = new String[6];
                        k = 0;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
