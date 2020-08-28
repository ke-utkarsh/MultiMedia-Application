package com.example.projecttwo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class vdo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_vdo);
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(vdo.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        String val=getIntent().getStringExtra("url");
        WebView wb=(WebView)findViewById(R.id.webView);
        wb.setWebViewClient(new WebViewClient());
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wb.getSettings().setPluginState(WebSettings.PluginState.ON);
        wb.getSettings().setMediaPlaybackRequiresUserGesture(true);
        wb.setWebChromeClient(new WebChromeClient());
        wb.loadUrl(val);

        progressDialog.dismiss();

    }
}
