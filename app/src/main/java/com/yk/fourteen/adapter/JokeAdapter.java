package com.yk.fourteen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yk.fourteen.R;
import com.yk.fourteen.bean.Joke;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YAY on 2017/5/15.
 */

public class JokeAdapter extends BaseAdapter {
    public List<Joke.ShowapiResBodyBean.ContentlistBean> data;
    public ViewHolder viewHolder;

    public JokeAdapter(List<Joke.ShowapiResBodyBean.ContentlistBean> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_joke, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvCardViewJoke.setText(data.get(position).getText());
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_cardView_joke)
        TextView tvCardViewJoke;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public void setNewData(List<Joke.ShowapiResBodyBean.ContentlistBean> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    public void setMoreData(List<Joke.ShowapiResBodyBean.ContentlistBean> newData) {
        data.addAll(newData);
        notifyDataSetChanged();
    }
}
