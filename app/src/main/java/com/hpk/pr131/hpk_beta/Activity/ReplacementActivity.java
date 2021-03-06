package com.hpk.pr131.hpk_beta.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.multidex.MultiDex;
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
import android.widget.Toast;

import com.hpk.pr131.hpk_beta.Constants;
import com.hpk.pr131.hpk_beta.Model.ReplaceModel;
import com.hpk.pr131.hpk_beta.R;

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
    private Map<String, List<ReplaceModel>> listOfObj;
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
        MultiDex.install(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( !isOnline() ){
                    Toast.makeText(getApplicationContext(),
                            "Немає з'єднання з мережею Інтернет!",Toast.LENGTH_LONG).show();
                }else {
                    progressDialog = new ProgressDialog(ReplacementActivity.this);
                    new ParseReplacement().execute();
                }
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
            is.close();
            fis.close();
            for (Map.Entry<String, List<ReplaceModel>> entry : listOfObj.entrySet()) {
                listGroup.add(entry.getKey());
                listModel.add(entry.getValue());
            }
            initCardViewInfo();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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


    private void initCardViewInfo() {
        TextView tv = (TextView) findViewById(R.id.dateReplace);
        assert tv != null;
        if (listModel.size() > 0) {
            int len = listModel.get(0).get(0).getDate().length();
            tv.setText(listModel.get(0).get(0).getDate().substring(len - 18, len));
        }
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridGroup);
        assert gridLayout != null;
        gridLayout.removeAllViews();
        Button btnGroup;
        int typeSize = display.getHeight() / 100;
        int total = listGroup.size();
        int column = 4;
        int row = total / column;
        gridLayout.setColumnCount(column);
        gridLayout.setRowCount(row + 1);
        for (int i = 0, c = 0, r = 0; i < total; i++, c++) {
            if (c == column) {
                c = 0;
                r++;
            }
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = display.getHeight() / 15;
            param.width = display.getWidth() / (column + 2);
            param.rightMargin = 5;
            if (c == 0) {
                param.leftMargin = display.getWidth() / (column + 4);
            }
            if (c + 1 == column) {
                param.rightMargin = display.getWidth() / (column + 4);
            }
            param.setGravity(Gravity.CENTER_HORIZONTAL);
            param.columnSpec = GridLayout.spec(c);
            param.rowSpec = GridLayout.spec(r);

            btnGroup = new Button(this);
            btnGroup.setText(listGroup.get(i));
            btnGroup.setTextSize(typeSize);
            btnGroup.setBackground(null);
            btnGroup.setTextColor(getResources().getColorStateList(R.color.light_grey));
            btnGroup.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btnGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout linearReplace = (LinearLayout) findViewById(R.id.linearReplace);
                    assert linearReplace != null;
                    linearReplace.removeAllViews();
                    int typeSize = display.getHeight() / 90;
                    ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    param.height = display.getHeight() / 20;
                    param.width = display.getWidth() / 10;

                    List<ReplaceModel> ll = listOfObj.get(((Button) v).getText());
                    int i = 1;
                    for (ReplaceModel rm : ll) {
                        //Log.e("samuliak", "ll.getPair" + rm.getPair());
                        if (rm.getGroup() == ((Button) v).getText()) {
                            TextView tv = new TextView(v.getContext());
                            tv.setText(i + ") Група: " + rm.getGroup() + "    |    " + "Пара.:" + rm.getPair()
                                    + "    |    " + "Ауд.:" + rm.getAudience() + "    |    " + "Предмет:" + rm.getSubject()
                                    + "    |    " + "Замінено:" + rm.getReplaced() + "    |    " + "Викладач:" + rm.getTeacher());
                            tv.setTextSize(typeSize);
                            tv.setTextColor(getResources().getColorStateList(R.color.light_grey));
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            linearReplace.addView(tv);
                            i++;
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
            progressDialog = new ProgressDialog(ReplacementActivity.this);
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
            progressDialog.dismiss();
            listModel.clear();
            listGroup.clear();
            try {
                FileOutputStream fos = openFileOutput(Constants.FILE_REPLACEMENT, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(listOfObj);
                os.flush();
                os.close();
                fos.close();
                for (Map.Entry<String, List<ReplaceModel>> entry : listOfObj.entrySet()) {
                    listGroup.add(entry.getKey());
                    listModel.add(entry.getValue());
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
                int k = 0;
                ReplaceModel model;
                String[] str = new String[6];
                List<ReplaceModel> list = new ArrayList<>();
                int count = 0;
                for (int i = 0; i < elementParse.size(); i++) {
                    if (i > 5) {
                        //переробити логіку зчитування
                        if (i + 1 < elementParse.size()) {
                            if (elementParse.get(i).text().length() < 2 && elementParse.get(i + 1).text().length() < 2
                                    && elementParse.get(i + 2).text().length() < 2 && elementParse.get(i + 3).text().length() < 2
                                    && elementParse.get(i + 4).text().length() < 2 && elementParse.get(i + 5).text().length() < 2) {
                                i = i + 6;
                            } else {
                                if (elementParse.get(i).text().length() < 1 && elementParse.get(i - 1).text().length() < 1
                                        && elementParse.get(i - 2).text().length() < 1 && elementParse.get(i - 3).text().length() < 1
                                        && elementParse.get(i - 4).text().length() < 1 && elementParse.get(i - 5).text().length() < 1
                                        && elementParse.get(i + 1).text().length() > 1) {
                                    i = i + 1;
                                } else {
                                    if (elementParse.get(i).text().length() < 2 && elementParse.get(i + 1).text().length() < 2
                                            && elementParse.get(i + 2).text().length() > 20 && k == 0) {
                                        String group = elementParse.get(i + 2).text().substring(0, 6);
                                        model = new ReplaceModel(group, "", "", "", "", "");
                                        addModelToList(model, list, count);
                                        count++;
                                        str = new String[6];
                                        k = 0;
                                        i = i + 3;
                                        continue;
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
                                        if (i + 1 < elementParse.size()) {
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
                        }
                    }
                }
                elementParse = doc.select(".news-body p");
                for (Map.Entry<String, List<ReplaceModel>> entry : listOfObj.entrySet()) {
                    for(int i=0; i < entry.getValue().size(); i++ ) {
                        entry.getValue().get(i).setDate(elementParse.get(0).text());
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
                Log.e("samuliak", "list.get(count)"+list.get(count).getPair());
                listOfObj.put(list.get(count).getGroup(), list);
            } else {
                List<ReplaceModel> rm = listOfObj.get(list.get(count).getGroup());
                rm.add(model);
                listOfObj.put(list.get(count).getGroup(), rm);
            }
            listModel.add(list);
        }
    }
}
