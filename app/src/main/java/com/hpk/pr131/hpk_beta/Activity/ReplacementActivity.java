package com.hpk.pr131.hpk_beta.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hpk.pr131.hpk_beta.Constants;
import com.hpk.pr131.hpk_beta.Model.ReplaceModel;
import com.hpk.pr131.hpk_beta.R;

import org.apache.poi.ss.formula.functions.Replace;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplacementActivity extends AppCompatActivity {
    private volatile Map<String, List<ReplaceModel>> listOfObj;
    private List<String> listGroup;
    private List<List<ReplaceModel>> listModel;
    private ProgressDialog progressDialog;
    private Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(ReplacementActivity.this);
                new ParseReplacement().execute();
            }
        });
        listOfObj = new HashMap<>();
        listGroup = new ArrayList<>();
        listModel = new ArrayList<>();
        display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        try {
            FileInputStream fis = openFileInput(Constants.FILE_REPLACEMENT);
            ObjectInputStream is = new ObjectInputStream(fis);
            listOfObj = (Map<String, List<ReplaceModel>>) is.readObject();
            for (String key : listOfObj.keySet())
                listGroup.add(key);
            is.close();
            fis.close();
            initCardViewInfo();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initCardViewInfo() {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridGroup);
        assert gridLayout != null;
        gridLayout.removeAllViews();
        Button btnGroup;
        int typeSize = display.getHeight()/135;
        int total = listGroup.size();
        int column = 4;
        int row = total / column;
        gridLayout.setColumnCount(column);
        gridLayout.setRowCount(row + 1);

        for(int i = 0, c = 0, r = 0; i < total; i++,c++){
            if(c == column) {
                c = 0;
                r++;
            }
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = display.getHeight() / 15;
            param.width = display.getWidth() / (column+2);
            param.rightMargin = 5;
            if (c == 0){
                param.leftMargin = display.getWidth() / (column+4);
            }
            if (c+1 == column){
                param.rightMargin = display.getWidth() / (column+4);
            }
            param.setGravity(Gravity.CENTER_HORIZONTAL);
            param.columnSpec = GridLayout.spec(c);
            param.rowSpec = GridLayout.spec(r);

            btnGroup = new Button(this);
            btnGroup.setText(listGroup.get(i));
            btnGroup.setTextSize(typeSize);
            btnGroup.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btnGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("samuliak", "Click is OK");
                    //CardView cardReplace = (CardView) findViewById(R.id.cardReplace);
                    //assert cardReplace != null;
                    //cardReplace.setVisibility(View.VISIBLE);
                    LinearLayout linearReplace = (LinearLayout) findViewById(R.id.linearReplace);
                    assert linearReplace != null;
                    linearReplace.removeAllViews();
                    int typeSize = display.getHeight() / 110;
                    ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    param.height = display.getHeight() / 20;
                    param.width = display.getWidth() / 10;

                    List<ReplaceModel> ll = listOfObj.get(((Button) v).getText());
                   for (ReplaceModel rm : ll) {
                       if (rm.getGroup() == ((Button) v).getText()) {
                           TextView tv = new TextView(v.getContext());
                           tv.setText(rm.toString());
                           tv.setTextSize(typeSize);
                           tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                           linearReplace.addView(tv);
                       }
                   }

                }
            });
            btnGroup.setLayoutParams(param);
            gridLayout.addView(btnGroup);
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
            listModel.clear();
            listGroup.clear();
            try {
                FileOutputStream fos = openFileOutput(Constants.FILE_REPLACEMENT, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(listOfObj);
                os.flush();
                os.close();
                fos.close();
                Log.e("samuliak", "onPostExecute - listofObj.size(): : "+listOfObj.size());
                for( Map.Entry<String, List<ReplaceModel>> entry : listOfObj.entrySet() ){
                    listGroup.add(entry.getKey());
                    listModel.add(entry.getValue());
                }

                for(List<ReplaceModel> list : listModel){
                    Log.e("samuliak", "onPostExecute - list.size(): : "+list.size());
                    for(ReplaceModel rm : list)
                        Log.e("samuliak", "onPostExecute - rm.toString(): : "+rm.toString());
                }

                initCardViewInfo();
            } catch (IOException e) {
                e.printStackTrace();
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
            listOfObj.clear();
            listGroup.clear();
            listModel.clear();
            try {
                doc = Jsoup.connect(Constants.URL_REPLACEMENT).get();
                elementParse = doc.select(".news-body td");
                int k=0;
                ReplaceModel model;
                String[] str = new String[6];
                List<ReplaceModel> list = new ArrayList<>();
                Log.e("samuliak", "///////////////////////////////////////");
                int count = 0;
                for(int i=0; i<elementParse.size(); i++){
                    if ( i > 5) {
                        if (i+1 < elementParse.size()) {
                            if (elementParse.get(i).text().length() < 2 && elementParse.get(i + 1).text().length() < 2
                                    && elementParse.get(i + 2).text().length() > 20 && k == 0) {
                                String group = elementParse.get(i + 2).text().substring(0, 6);
                                model = new ReplaceModel(group, "", "", "", "", "");
                                list.add(model);
                                addModelToList(model, list, count);
                                count++;
                                str = new String[6];
                                k = 0;
                                i = i + 3;
                                continue;
                            }
                        }
                        if (elementParse.get(i).text().length() < 4 && elementParse.get(i - 1).text().length() < 5 && k == 0 ||
                                elementParse.get(i).text().length() < 4 && elementParse.get(i - 1).html().contains("p") && k == 0) {
                            if (elementParse.get(i - 6).text().length() < 5) {
                                str[k] = elementParse.get(i - 11).text();
                                k++;
                            } else {
                                str[k] = elementParse.get(i - 6).text();
                                k++;
                            }
                        } else {
                            if(i+1<elementParse.size()) {
                                if (elementParse.get(i).text().length() < 2 && elementParse.get(i + 1).text().length() > 20 && k == 1) {
                                    str[k] = "";
                                    k++;
                                    str[k] = elementParse.get(i + 1).text();
                                    k = 8;
                                    if (elementParse.get(i + 2).text().length() < 2 && elementParse.get(i + 3).text().length() > 5) {
                                        i = i + 2;
                                    }
                                }
                            }
                        }
                        if (k <= 6) {
                            str[k] = elementParse.get(i).text();
                            k++;
                        }

                        if (k > 5) {
                            model = new ReplaceModel(str[0], str[1], str[2], str[3], str[4], str[5]);
                            addModelToList(model, list, count);
                            count++;
                            str = new String[6];
                            k = 0;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void addModelToList(ReplaceModel model, List<ReplaceModel> list, int count) {
            list.add(model);
            if (!listOfObj.containsKey(list.get(count).getGroup())) {
                listOfObj.put(list.get(count).getGroup(), list);
            }
            else {
                List<ReplaceModel> rm = listOfObj.get(list.get(count).getGroup());
                rm.add(model);
                listOfObj.put(list.get(count).getGroup(), rm);
            }
            listModel.add(list);
            //list.clear();
        }
    }
}
