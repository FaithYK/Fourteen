package com.yk.fourteen.ui.fragment;

import android.content.DialogInterface;
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

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yk.fourteen.R;
import com.yk.fourteen.adapter.JokeAdapter;
import com.yk.fourteen.bean.Account;
import com.yk.fourteen.bean.Collection;
import com.yk.fourteen.bean.Joke;
import com.yk.fourteen.common.Common;
import com.yk.fourteen.common.ServerConfig;
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
 * Created by YAY on 2017/5/15.
 */

public class JokeFragment extends Fragment {
    public static final int TYPE_REFRESH = 0X01;
    public static final int TYPE_LOADMORE = 0X02;

    @BindView(R.id.prflv_listview_joke)
    PullToRefreshListView prflvListviewJoke;
    Unbinder unbinder;
    private View view;

    private static int page = 1;
    private JokeAdapter jokeAdapter;
    private List<Joke.ShowapiResBodyBean.ContentlistBean> data = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_joke, null);
        unbinder = ButterKnife.bind(this, view);

        initViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }


    private void initData() {
        getSyncData(1, TYPE_REFRESH);
    }

    private void getSyncData(final int page, final int type) {
        OkHttpUtils.get()
                .url(ServerConfig.YIYUAN_SHUJU_URL)
                .addParams("showapi_appid", Common.YIYUAN_APPID)
                .addParams("showapi_sign", Common.YIYUAN_KEY)
                .addParams("maxResult", Common.PAGE_SIZE)
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "请求失败！", Toast.LENGTH_SHORT).show();
                        prflvListviewJoke.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        prflvListviewJoke.onRefreshComplete();
                        Joke joke = JSON.parseObject(response, Joke.class);
                        switch (type) {
                            case TYPE_REFRESH:
                                jokeAdapter.setNewData(joke.getShowapi_res_body().getContentlist());
                                break;
                            case TYPE_LOADMORE:
                                jokeAdapter.setMoreData(joke.getShowapi_res_body().getContentlist());
                                break;
                        }
                    }
                });

    }
    private void initViews() {
        //设置列表的刷新加载
        prflvListviewJoke.setMode(PullToRefreshBase.Mode.BOTH);
        //初始化适配器
        jokeAdapter = new JokeAdapter(data);
        //绑定适配器
        prflvListviewJoke.setAdapter(jokeAdapter);

        //添加监听事件
        prflvListviewJoke.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), data.get(position - 1).getText(), Toast.LENGTH_SHORT).show();
            }
        });
        prflvListviewJoke.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getContext())
                        .setTitle("收藏")
                        .setMessage("是否收藏？")
                        .setPositiveButton("收藏", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LoginUtils.checkLogin(true);
                                Account account = BmobUser.getCurrentUser(BaseApplication.getInstance(),Account.class);
                                if (account != null) {
                                    Collection collection = new Collection();
                                    collection.setuId(account.getObjectId());
                                    collection.setType(Common.COLLECTION_TYPE_JOKE);
                                    collection.setTitle(data.get(position - 1).getTitle());
                                    saveCollectionData(collection);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
                return false;
            }
        });
        prflvListviewJoke.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
    private void saveCollectionData(Collection collection) {
        collection.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(),"收藏成功!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getContext(),"收藏失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
