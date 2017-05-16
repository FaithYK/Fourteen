package com.yk.fourteen.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yk.fourteen.R;
import com.yk.fourteen.bean.News;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends AppCompatActivity {
    @BindView(R.id.wv_detail)
    WebView wv_detail;
    private Intent intent;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        setEvent();
    }

    private void setEvent() {
        intent = getIntent();
        news = intent.getExtras().getParcelable("news");
        wv_detail.loadUrl(news.getUrl());
        WebSettings settings = wv_detail.getSettings();
        settings.setJavaScriptEnabled(true);
        wv_detail.setWebChromeClient(new WebChromeClient());
        wv_detail.setWebViewClient(new WebViewClient());
    }
}
