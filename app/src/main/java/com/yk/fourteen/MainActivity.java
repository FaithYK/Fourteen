package com.yk.fourteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yk.fourteen.ui.activity.NavigationActivity;
import com.yk.fourteen.ui.activity.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.login_photo)
    RoundedImageView loginPhoto;
    @BindView(R.id.login_name)
    EditText loginName;
    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.textInputLayout2)
    TextInputLayout textInputLayout2;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.cl_layout)
    ConstraintLayout clLayout;
    @BindView(R.id.ibtn_sina)
    ImageView ibtnSina;
    @BindView(R.id.ibtn_qq)
    ImageView ibtnQq;
    @BindView(R.id.ibtn_wechat)
    ImageView ibtnWechat;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login, R.id.tv_password, R.id.tv_register,R.id.ibtn_sina, R.id.ibtn_qq, R.id.ibtn_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                EndLogin();
                break;
            case R.id.tv_password:
                break;
            case R.id.tv_register:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.ibtn_sina:
               SinaLogin();
                break;
            case R.id.ibtn_qq:
                break;
            case R.id.ibtn_wechat:
                break;
        }
    }

    private void SinaLogin() {

    }

    private void EndLogin() {
        String name = loginName.getText().toString();
        String pwd = loginPassword.getText().toString();
        BmobUser user = new BmobUser();
        user.setUsername(name);
        user.setPassword(pwd);
        user.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                intent = new Intent(MainActivity.this, NavigationActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this, "账户密码错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
