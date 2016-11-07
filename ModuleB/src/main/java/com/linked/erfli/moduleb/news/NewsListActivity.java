package com.linked.erfli.moduleb.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.EventPool;
import com.linked.erfli.library.utils.Utils;
import com.linked.erfli.library.utils.operators.AppObservable;
import com.linked.erfli.library.widget.DividerOffsetDecoration;
import com.linked.erfli.library.widget.PullToRefreshLayout;
import com.linked.erfli.library.widget.RecyclerItemClickListener;
import com.linked.erfli.library.widget.RefreshLayout;
import com.linked.erfli.moduleb.R;
import com.linked.erfli.moduleb.utils.ZhihuApiHttp;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.functions.Action1;

@Router("news_list")
public class NewsListActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private List<Story> myDataset;
    private NewsAdapter mAdapter;
    RefreshLayout refreshLayout;

    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module2_activity_recycler_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.module2_latest_news);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initRefreshView();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerOffsetDecoration());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        initData();
    }

    private void initRefreshView() {
        refreshLayout = (RefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLatestData();
            }
        });
        refreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                getHistoryData();
            }
        });
    }

    private void initData() {
        myDataset = new ArrayList<>();
        mAdapter = new NewsAdapter(this, myDataset);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(NewsListActivity.this, NewsDetailActivity.class);
//                intent.putExtra(NewsDetailActivity.NEWS, mAdapter.getItem(position));
//                startActivity(intent);
                Story story = mAdapter.getItem(position);
                Routers.open(NewsListActivity.this, "modularization://news_detail/" + story.getId() + "/" + Utils.encodeUrlParam(story.getTitle()));
            }
        }));
        getLatestData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    private void getLatestData() {
        page = 0;
        getStoryList("");
    }

    private void getHistoryData() {
        String time = getDateString(new Date());
        String key = String.valueOf(Long.valueOf(time) - page);
        page += 1;
        getStoryList(key);
    }

    private void getStoryList(String newsKey) {
        if (TextUtils.isEmpty(newsKey)) {
            AppObservable.bindActivity(this, ZhihuApiHttp.http.getLatestNews()).subscribe(new Action1<NewsResponse>() {
                @Override
                public void call(NewsResponse newsResponse) {
                    refreshLayout.setRefreshing(false);
                    myDataset.clear();
                    myDataset.addAll(newsResponse.getStories());
                    mAdapter.notifyDataSetChanged();
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Toast.makeText(NewsListActivity.this, "network error", Toast.LENGTH_SHORT).show();
                    refreshLayout.setRefreshing(false);
                }
            });
        } else {
            AppObservable.bindActivity(this, ZhihuApiHttp.http.getHistoryNews(newsKey)).subscribe(new Action1<NewsResponse>() {
                @Override
                public void call(NewsResponse newsResponse) {
                    refreshLayout.setRefreshing(false);
                    myDataset.addAll(newsResponse.getStories());
                    mAdapter.notifyDataSetChanged();
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Toast.makeText(NewsListActivity.this, "network error", Toast.LENGTH_SHORT).show();
                    refreshLayout.setRefreshing(false);
                }
            });
        }
    }

    public static String getDateString(Date date) {
        String year = (date.getYear() + 1900) + "";
        String mm = (date.getMonth() + 1) + "";
        if (Integer.valueOf(mm).intValue() < 10) {
            mm = "0" + mm;
        }
        String day = date.getDate() + "";
        if (Integer.valueOf(day).intValue() < 10) day = "0" + day;
        return year + mm + day;
    }

}
