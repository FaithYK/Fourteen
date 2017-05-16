package com.yk.fourteen.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yk.fourteen.R;
import com.yk.fourteen.adapter.PhotoAdapter;
import com.yk.fourteen.bean.Photo;
import com.yk.fourteen.bean.Photo.ShowapiResBodyBean.PagebeanBean.ContentlistBean;
import com.yk.fourteen.common.Common;
import com.yk.fourteen.common.ServerConfig;
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

public class PhotoFragment extends Fragment {
    @BindView(R.id.rlv_photo)
    RecyclerView rlv_photo;
    @BindView(R.id.refresh_photo)
    SwipeRefreshLayout refresh_photo;
    Unbinder unbinder;


    private int page = 1;
    private View rootView;
    private PhotoAdapter photoAdapter;
    private List<ContentlistBean> data = new ArrayList<>();
    //交错式网格布局管理对象，即通常称的瀑布流布局
    private StaggeredGridLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frament_photo, null);
        unbinder = ButterKnife.bind(this, rootView);

        initView();//实例化Adapter
        return rootView;
    }

    private void initView() {
        photoAdapter = new PhotoAdapter(getContext(),data);
        rlv_photo.setAdapter(photoAdapter);
        //实例化SwipeRefreshLayout
        mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rlv_photo.setLayoutManager(mLayoutManager);
        //调整SwipeRefreshLayout的位置
        refresh_photo.setProgressViewOffset(false, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

    }

    @Override
    public void onResume() {
        super.onResume();
        initData(1);
        setListener();
    }
    private void initData(final int page) {
        OkHttpUtils.get()
                .url(ServerConfig.YIYUAN_PHOTO_URL)
                .addParams("showapi_appid", Common.YIYUAN_APPID)
                .addParams("showapi_sign", Common.YIYUAN_Joke_KEY)
                .addParams("type", String.valueOf(4002))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "请求失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Photo photo = JSON.parseObject(response, Photo.class);
                        data.addAll(photo.getShowapi_res_body().getPagebean().getContentlist());
                        photoAdapter.notifyDataSetChanged();
                    }

                });
    }
    private void setListener() {
        //swipeRefreshLayout刷新监听
        refresh_photo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_photo.setRefreshing(false);
                page = 1;
                initData(page);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
