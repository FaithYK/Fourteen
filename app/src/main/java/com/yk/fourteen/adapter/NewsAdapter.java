package com.yk.fourteen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yk.fourteen.R;
import com.yk.fourteen.bean.News;

import java.util.List;

/**
 * Created by S01 on 2017/5/4.
 */

public class NewsAdapter extends BaseAdapter {
    public List<News> data;
    public ViewHolder viewHolder;

    public NewsAdapter(Context context, int item_news, ListView lv_news) {
    }

    public NewsAdapter(List<News> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_news,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        //加载网络图片
        Glide.with(viewGroup.getContext())
                .load(data.get(i).getThumbnail_pic_s())
                .centerCrop()
                .placeholder(R.mipmap.aio_image_default_round)
                .error(R.mipmap.aio_image_fail_round)
                .crossFade()
                .into(viewHolder.iv_photo);
        viewHolder.tv_title.setText(data.get(i).getTitle());
        return view;
    }
    class ViewHolder{
            ImageView iv_photo;
            TextView tv_title;
            public ViewHolder(View view){
                iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
                tv_title = (TextView) view.findViewById(R.id.tv_title);
            }
    }
    public void setNewData(List<News> list){
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }
    public void setMoreData(List<News> list){
        data.addAll(list);
        notifyDataSetChanged();
    }
}
