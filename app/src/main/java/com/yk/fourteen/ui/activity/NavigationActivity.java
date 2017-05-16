package com.yk.fourteen.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yk.fourteen.R;
import com.yk.fourteen.ui.fragment.JokeFragment;
import com.yk.fourteen.ui.fragment.MyselfFragment;
import com.yk.fourteen.ui.fragment.NewsFragment;
import com.yk.fourteen.ui.fragment.PhotoFragment;
import com.yk.fourteen.ui.widget.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationActivity extends AppCompatActivity {

    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.navigation)
    BottomNavigationViewEx navigation;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private NewsFragment newsFragment;
    private JokeFragment jokeFragment;
    private PhotoFragment photoFragment;
    private MyselfFragment myselfFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        initView();
    }

    private void initView() {
        navigation.enableAnimation(false);
        navigation.enableItemShiftingMode(false);
        navigation.enableShiftingMode(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //实例化Fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        newsFragment = new NewsFragment();
        fragmentTransaction.replace(R.id.content, newsFragment);
        tvTitle.setText(R.string.title_news);
        fragmentTransaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    newsFragment = new NewsFragment();
                    tvTitle.setText(R.string.title_news);
                    replaceFragment(newsFragment);
                    return true;
                case R.id.navigation_joke:
                    jokeFragment = new JokeFragment();
                    tvTitle.setText(R.string.title_joke);
                    replaceFragment(jokeFragment);
                    return true;
                case R.id.navigation_photo:
                    photoFragment = new PhotoFragment();
                    tvTitle.setText(R.string.title_photo);
                    replaceFragment(photoFragment);
                    return true;
                case R.id.navigation_my:
                    myselfFragment = new MyselfFragment();
                    tvTitle.setText(R.string.title_my);
                    replaceFragment(myselfFragment);
                    return true;
            }
            return false;
        }

    };

    private void replaceFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

}
