package com.hpk.pr131.hpk_beta;


import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


public class SendFragment extends DialogFragment implements View.OnClickListener {
    EditText title, text;
    String subject, textMessage;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Зворотній зв'язок!");
        View v = inflater.inflate(R.layout.send_fragment, null);
        v.findViewById(R.id.btnSendMessage).setOnClickListener(this);
        title = (EditText) v.findViewById(R.id.title);
        text = (EditText) v.findViewById(R.id.textSend);
        return v;
    }

    public void onClick(View v) {
        subject = title.getText().toString();
        textMessage = text.getText().toString();
        Log.e("samuliak", "Start task.");
        //Example #1
//        Intent i = new Intent(Intent.ACTION_SEND);
//        i.setType("message/rfc822");
//        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"r.samuliak@yandex.ua"});
//        i.putExtra(Intent.EXTRA_SUBJECT, subject);
//        i.putExtra(Intent.EXTRA_TEXT   , textMessage);
//        try {
//            startActivity(Intent.createChooser(i, "Відправка пошти..."));
//        } catch (android.content.ActivityNotFoundException ex) {}
//        dismiss();

        //Example #2
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Log.e("samuliak", "new Thread is starting");
                    EMailSender sender = new EMailSender("r.samuliak@mail.ru", "microlab1", "94.100.177.1", 2525);
                    sender.sendMail("r.samuliak@gmail.com", subject, textMessage);
                } catch (Exception e) {
                    Log.e("test", "send error > " + e.toString());
                }
            }
        }).start();
        this.dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}





