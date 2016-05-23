package com.hpk.pr131.hpk_beta;

import android.os.SystemClock;
import android.util.Base64;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EMailSender {

    private OutputStream _out;
    private Socket _socket;
    private String _sender;
    private String _password;
    private String _smtpAddress;
    private int _port;

    public EMailSender(String sender, String password, String smtpAddress, int port) {
        _sender = sender;
        _password = password;
        _smtpAddress = smtpAddress;
        _port = port;
    }

    public synchronized void sendMail(String recipients, String title, String body) throws IOException {
        connect();
        String plain = "\0" + _sender + "\0" + _password;
        String plain64 = Base64.encodeToString(plain.getBytes(), Base64.DEFAULT);
        write("EHLO " + "nerivne" + "\r\n");
        write("AUTH PLAIN " + plain64 + "\r\n");
        SystemClock.sleep(500);
        write("MAIL FROM:<" + _sender + "> SIZE=" + body.length() + "\r\n");
        write("RCPT TO:<" + recipients + ">\r\n");
        SystemClock.sleep(500);
        write("DATA\r\n");
        write("From: <"+ _sender +">\r\n" +
                "To: <" + recipients + ">\r\n" +
                "Subject: " + title + "\r\n" +
                "Content-Type: text/plain" + ";\r\n" +
                "\tcharset=\"utf-8\"\r\n");
        write("\r\n" + body + "\r\n.\r\n");
        write("RSET\r\n");
        write("QUIT\r\n");
        SystemClock.sleep(1500);
        disconnect();
    }


    private void connect() throws IOException {
        _socket = new Socket();
        InetSocketAddress sockaddr = new InetSocketAddress(_smtpAddress, _port);
        _socket.connect(sockaddr, 4000);
        _out = _socket.getOutputStream();
    }

    private void disconnect() throws IOException {
        _out.close();
        _socket.close();
    }

    private void write(String str) throws IOException {
        _out.write(str.getBytes());
    }

}