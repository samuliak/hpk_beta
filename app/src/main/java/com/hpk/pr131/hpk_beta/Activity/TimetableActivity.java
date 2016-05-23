package com.hpk.pr131.hpk_beta.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hpk.pr131.hpk_beta.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TimetableActivity extends AppCompatActivity {

    private InputStream in = null;
    private OutputStream out = null;
    private AssetManager assetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        assetManager = getAssets();
    }

    public void onClick1k(View view)
    {
        File file = new File(getFilesDir(), "kurs1.pdf");
        try
        {
            in = assetManager.open("kurs1.pdf");
            out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
            copyPdfFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("exception", e.getMessage());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + getFilesDir() + "/kurs1.pdf"),
                "application/pdf");
        startActivity(intent);
    }

    public void onClick2k(View view)
    {
        File file = new File(getFilesDir(), "kurs2.pdf");
        try
        {
            in = assetManager.open("kurs2.pdf");
            out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
            copyPdfFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("exception", e.getMessage());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + getFilesDir() + "/kurs2.pdf"),
                "application/pdf");
        startActivity(intent);
    }
    public void onClick3k(View view)
    {
        File file = new File(getFilesDir(), "kurs3.pdf");
        try
        {
            in = assetManager.open("kurs3.pdf");
            out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
            copyPdfFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("exception", e.getMessage());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + getFilesDir() + "/kurs3.pdf"),
                "application/pdf");
        startActivity(intent);
    }

    public void onClick4k(View view)
    {
        File file = new File(getFilesDir(), "kurs4.pdf");
        try
        {
            in = assetManager.open("kurs4.pdf");
            out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
            copyPdfFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("exception", e.getMessage());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + getFilesDir() + "/kurs4.pdf"),
                "application/pdf");
        startActivity(intent);
    }

    public void onCLickZaoch(View view) {
        File file = new File(getFilesDir(), "zaoch.pdf");
        try
        {
            in = assetManager.open("zaoch.pdf");
            out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
            copyPdfFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("exception", e.getMessage());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + getFilesDir() + "/zaoch.pdf"),
                "application/pdf");
        startActivity(intent);
    }
    private void copyPdfFile(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }
}
