package com.linked.erfli.moduleb.news;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.mzule.activityrouter.annotation.Router;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.Utils;
import com.linked.erfli.library.utils.operators.AppObservable;
import com.linked.erfli.moduleb.R;
import com.linked.erfli.moduleb.utils.ZhihuApiHttp;

import rx.functions.Action1;

@Router("news_detail/:id/:title")
public class NewsDetailActivity extends BaseActivity {
    private WebView webView;
    private ImageView titleImageView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module2_activity_news_detail);
        initView();
    }

    protected void initView() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        titleImageView = (ImageView) findViewById(R.id.ivImage);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        collapsingToolbarLayout.setTitle(Utils.decodeUrlParam(getIntent().getStringExtra("title")));
        AppObservable.bindActivity(this, ZhihuApiHttp.http.getNewsDetail(getIntent().getStringExtra("id"))).subscribe(new Action1<NewsDetailResponse>() {
            @Override
            public void call(final NewsDetailResponse value) {
                collapsingToolbarLayout.setTitle(value.getTitle());
                Glide.with(NewsDetailActivity.this)
                        .load(value.getImage())
                        .asBitmap()
                        .into(titleImageView);
                if (value.getCss() != null && value.getCss().size() > 0) {
                    String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/news.css\" type=\"text/css\">";
                    String html = "<html><head>" + css + "</head><body>" + value.getBody() + "</body></html>";
                    html = html.replace("<div class=\"img-place-holder\">", "");
                    webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "UTF-8", null);
                } else {
                    webView.loadData(value.getBody(), "text/html", "utf-8");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Utils.showShortToast(NewsDetailActivity.this, "network error");
            }
        });
    }

}
