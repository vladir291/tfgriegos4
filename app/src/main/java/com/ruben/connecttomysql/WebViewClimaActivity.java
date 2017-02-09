package com.ruben.connecttomysql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewClimaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_clima);



        WebView myWebView = (WebView) findViewById(R.id.webView);

        String busqueda =(String) getIntent().getSerializableExtra("busqueda");
        myWebView.loadUrl("https://www.eltiempo.es/buscar?q="+busqueda);
    }
}
