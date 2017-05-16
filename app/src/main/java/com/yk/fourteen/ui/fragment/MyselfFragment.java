package com.yk.fourteen.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.yk.fourteen.MainActivity;
import com.yk.fourteen.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by YAY on 2017/5/16.
 */

public class MyselfFragment extends Fragment {

    @BindView(R.id.titleText)
    TextView titleText;
    @BindView(R.id.top_relative)
    RelativeLayout topRelative;
    @BindView(R.id.btn_my_login)
    Button btnMyLogin;
    @BindView(R.id.more_page_row0)
    TableRow morePageRow0;
    @BindView(R.id.MorePageTableLayout_Favorite)
    TableLayout MorePageTableLayoutFavorite;
    @BindView(R.id.more_page_row1)
    TableRow morePageRow1;
    @BindView(R.id.more_page_row2)
    TableRow morePageRow2;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.more_page_row3)
    TableRow morePageRow3;
    @BindView(R.id.MorePageTableLayout_Follow)
    TableLayout MorePageTableLayoutFollow;
    @BindView(R.id.more_page_row4)
    TableRow morePageRow4;
    @BindView(R.id.more_page_row5)
    TableRow morePageRow5;
    @BindView(R.id.more_page_row6)
    TableRow morePageRow6;
    @BindView(R.id.more_page_row7)
    TableRow morePageRow7;
    @BindView(R.id.MorePageTableLayout_Client)
    TableLayout MorePageTableLayoutClient;
    @BindView(R.id.empty_cart_view)
    RelativeLayout emptyCartView;
    Unbinder unbinder;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_myself, null);

        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick({R.id.btn_my_login, R.id.textView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_my_login:
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
            case R.id.textView:
                break;
        }
    }
}
