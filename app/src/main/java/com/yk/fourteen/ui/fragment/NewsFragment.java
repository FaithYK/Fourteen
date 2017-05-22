package com.yk.fourteen.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yk.fourteen.R;
import com.yk.fourteen.adapter.NewsAdapter;
import com.yk.fourteen.bean.Account;
import com.yk.fourteen.bean.Collection;
import com.yk.fourteen.bean.News;
import com.yk.fourteen.common.Common;
import com.yk.fourteen.common.ServerConfig;
import com.yk.fourteen.ui.activity.NewsDetailActivity;
import com.yk.fourteen.utils.BaseApplication;
import com.yk.fourteen.utils.LoginUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
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

        //点击跳转到新闻详情

        prflv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("news", data.get(i - 1));
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        //长按收藏新闻
        prflv_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getContext())
                        .setTitle("收藏")
                        .setMessage("是否收藏？")
                        .setPositiveButton("收藏", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LoginUtils.checkLogin(true);
                                Account account = BmobUser.getCurrentUser(BaseApplication.getInstance(), Account.class);
                                    Collection collection = new Collection();
                                    collection.setuId(account.getObjectId());
                                    collection.setType(Common.COLLECTION_TYPE_NEWS);
                                    collection.setUrl(data.get(position - 1).getUrl());
                                    collection.setPicUrl(data.get(position - 1).getThumbnail_pic_s());
                                    collection.setTitle(data.get(position - 1).getTitle());
                                    saveCollectionData(collection);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
                return false;
            }
        });

        //实现刷新
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

    //收藏成功返回
    private void saveCollectionData(Collection collection) {
        collection.save(getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "收藏成功!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getContext(), "收藏失败!", Toast.LENGTH_SHORT).show();
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
