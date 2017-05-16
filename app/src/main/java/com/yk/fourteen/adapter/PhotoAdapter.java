package com.yk.fourteen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yk.fourteen.R;
import com.yk.fourteen.bean.Photo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by S01 on 2017/5/8.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    private MyViewHolder myViewHolder;
    private Context context;
    private List<Photo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> data = new ArrayList<>();

    public PhotoAdapter(Context context, List<Photo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_photo, parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //加载网络图片
        Glide.with(context)
                .load(data.get(position).getList().get(0).getBig())
                .centerCrop()
                .placeholder(R.mipmap.aio_image_default_round)
                .error(R.mipmap.aio_image_fail_round)
                .crossFade()
                .into(holder.iv_imageView);
        holder.tv_photo.setText(data.get(position).getTitle());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_imageView;
        TextView tv_photo;
        public MyViewHolder(View view) {
            super(view);
            iv_imageView = (ImageView) view.findViewById(R.id.iv_imageView);
            tv_photo = (TextView) view.findViewById(R.id.tv_photo);
        }
    }
}
