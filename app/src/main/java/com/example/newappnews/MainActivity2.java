package com.example.newappnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity2 extends AppCompatActivity {
    private WebView webView;
    public static String c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(c);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
    }
    public static void getContext(String ct){
        c=ct;
    }
}