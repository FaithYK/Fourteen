package com.yk.fourteen.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yk.fourteen.R;
import com.yk.fourteen.adapter.NewsAdapter;
import com.yk.fourteen.bean.News;
import com.yk.fourteen.common.Common;
import com.yk.fourteen.common.ServerConfig;
import com.yk.fourteen.ui.activity.NewsDetailActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by YAY on 2017/5/16.
 */

public class NewsFragment extends Fragment {
    public static final int TYPE_REFRESH = 0X01;
    public static final int TYPE_LOADMORE = 0X02;

    @BindView(R.id.prflv_listview)
    PullToRefreshListView prflv_listview;
    Unbinder unbinder;
    private View rootView;

    private int page = 1;
    private NewsAdapter newsAdapter;
    private List<News> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news, null);
        unbinder = ButterKnife.bind(this, rootView);

        initViews();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initViews() {
        newsAdapter = new NewsAdapter(data);
        prflv_listview.setAdapter(newsAdapter);
        prflv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("news", data.get(i));
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        prflv_listview.setMode(PullToRefreshBase.Mode.BOTH);
        prflv_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getSyncData(1, TYPE_REFRESH);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getSyncData(page++, TYPE_LOADMORE);
            }
        });
    }

    private void initData() {
        getSyncData(1, TYPE_REFRESH);
    }

    private void getSyncData(int page, final int type) {
        OkHttpUtils.get()
                .url(ServerConfig.BASE_URL)
                .addParams("type", "top")
                .addParams("page", String.valueOf(page))
                .addParams("key", Common.API_NEWS_KEY)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "请求失败！", Toast.LENGTH_SHORT).show();
                        prflv_listview.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        prflv_listview.onRefreshComplete();
                        switch (type) {
                            case TYPE_REFRESH:
                                newsAdapter.setNewData(data);
                                break;
                            case TYPE_LOADMORE:
                                newsAdapter.setMoreData(data);
                                break;
                        }
                        //把返回的结果json数据字符串转换成json对象
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        //获取data对应的数组数据
                        JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
                        //解析json数组为list对象
                        data.addAll(JSONArray.parseArray(jsonArray.toString(), News.class));
                        //通知adapter更新数据
                        newsAdapter.notifyDataSetChanged();
                    }

                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
