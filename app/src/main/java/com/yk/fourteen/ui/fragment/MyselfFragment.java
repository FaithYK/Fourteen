package com.yk.fourteen.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yk.fourteen.MainActivity;
import com.yk.fourteen.R;
import com.yk.fourteen.common.Common;
import com.yk.fourteen.common.PreferencesManager;
import com.yk.fourteen.ui.activity.PerfectInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;

/**
 * Created by YAY on 2017/5/16.
 */

public class MyselfFragment extends Fragment {


    @BindView(R.id.top_relative)
    RelativeLayout topRelative;
    @BindView(R.id.riv_my_photo)
    RoundedImageView rivMyPhoto;
    @BindView(R.id.btn_my_login)
    Button btnMyLogin;
    @BindView(R.id.tv_my_collection)
    TextView tvMyCollection;
    @BindView(R.id.tv_my_share)
    TextView tvMyShare;
    @BindView(R.id.MorePageTableLayout_Favorite)
    TableLayout MorePageTableLayoutFavorite;
    @BindView(R.id.tv_my_moon)
    TextView tvMyMoon;
    @BindView(R.id.more_page_row1)
    TableRow morePageRow1;
    @BindView(R.id.tv_my_ecord)
    TextView tvMyEcord;
    @BindView(R.id.more_page_row3)
    TableRow morePageRow3;
    @BindView(R.id.MorePageTableLayout_Follow)
    TableLayout MorePageTableLayoutFollow;
    @BindView(R.id.tv_my_client)
    TextView tvMyClient;
    @BindView(R.id.tv_my_install)
    TextView tvMyInstall;
    @BindView(R.id.more_page_row4)
    TableRow morePageRow4;
    @BindView(R.id.tv_my_feedBack)
    TextView tvMyFeedBack;
    @BindView(R.id.more_page_row5)
    TableRow morePageRow5;
    @BindView(R.id.tv_my_update)
    TextView tvMyUpdate;
    @BindView(R.id.more_page_row6)
    TableRow morePageRow6;
    @BindView(R.id.tv_my_about)
    TextView tvMyAbout;
    @BindView(R.id.more_page_row7)
    TableRow morePageRow7;
    @BindView(R.id.MorePageTableLayout_Client)
    TableLayout MorePageTableLayoutClient;
    @BindView(R.id.empty_cart_view)
    RelativeLayout emptyCartView;
    Unbinder unbinder;
    @BindView(R.id.tv_my_name)
    TextView tvMyName;
    private View rootView;
    private Intent intent;
    private BmobUser bmobUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_myself, null);
        unbinder = ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    protected void initData() {
        if (PreferencesManager.getInstance(getActivity()).get(Common.IS_LOGIN, false)) {
            rivMyPhoto.setVisibility(View.VISIBLE);
            tvMyName.setVisibility(View.VISIBLE);
            btnMyLogin.setVisibility(View.GONE);
            loadUserInfo();
        } else {
            rivMyPhoto.setVisibility(View.GONE);
            tvMyName.setVisibility(View.GONE);
            btnMyLogin.setVisibility(View.VISIBLE);
        }
    }

    private void loadUserInfo() {
        String userPhoto = PreferencesManager.getInstance(getActivity()).get(Common.USER_PHOTO);
        String userName = PreferencesManager.getInstance(getActivity()).get(Common.USER_NAME);
        tvMyName.setText(userName);
        Glide.get(rivMyPhoto.getContext()).with(rivMyPhoto.getContext())
                .load(userPhoto)
                .placeholder(R.mipmap.login_photo)
                .error(R.mipmap.aio_image_fail_round)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(rivMyPhoto);
    }

    @OnClick({R.id.riv_my_photo, R.id.btn_my_login, R.id.tv_my_collection, R.id.tv_my_share, R.id.tv_my_moon, R.id.tv_my_ecord, R.id.tv_my_client, R.id.tv_my_install, R.id.tv_my_feedBack, R.id.tv_my_update, R.id.tv_my_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.riv_my_photo:
                intent = new Intent(getActivity(), PerfectInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_my_login:
                intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                //隐藏登录按钮btn_my_login
                btnMyLogin.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_my_collection:
                break;
            case R.id.tv_my_share:
                break;
            case R.id.tv_my_moon:
                break;
            case R.id.tv_my_ecord:
                break;
            case R.id.tv_my_client:
                break;
            case R.id.tv_my_install:
                break;
            case R.id.tv_my_feedBack:
                break;
            case R.id.tv_my_update:
                break;
            case R.id.tv_my_about:
                showInfo();
                break;
        }
    }

    private void showInfo() {
        AlertDialog.Builder builer = new AlertDialog.Builder(getActivity())
                .setTitle("关于我们")
                .setMessage("开发人:WinFred\n地址:https://github.com/FaithYK/Fourteen")
                .setPositiveButton("确定", null);
        builer.create().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
