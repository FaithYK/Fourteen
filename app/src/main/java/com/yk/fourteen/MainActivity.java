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
import com.yk.fourteen.bean.Account;
import com.yk.fourteen.common.Common;
import com.yk.fourteen.common.PreferencesManager;
import com.yk.fourteen.ui.activity.NavigationActivity;
import com.yk.fourteen.ui.activity.RegisterActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

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
                QQLogin();
                break;
            case R.id.ibtn_wechat:
                Wechat();
                break;
        }
    }

    //微信授权登录
    private void Wechat() {
        ShareSDK.initSDK(this);
        Platform wechat= ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Toast.makeText(MainActivity.this,"授权成功！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        wechat.authorize();
    }
    //QQ授权登录
    private void QQLogin() {
        ShareSDK.initSDK(this);
        Platform qq= ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(cn.sharesdk.framework.Platform platform, int i, HashMap<String, Object> hashMap) {
                intent = new Intent(MainActivity.this, NavigationActivity.class);
                startActivity(intent);

            }

            @Override
            public void onError(cn.sharesdk.framework.Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(cn.sharesdk.framework.Platform platform, int i) {

            }
        });
        qq.authorize();
    }

    //新浪微博授权登录
    private void SinaLogin() {
        ShareSDK.initSDK(this);
        Platform sinaWeibo= ShareSDK.getPlatform(SinaWeibo.NAME);
        sinaWeibo.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Toast.makeText(MainActivity.this,"授权成功！",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
            }
            @Override
            public void onCancel(Platform platform, int i) {
            }
        });
        sinaWeibo.authorize();
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
//                intent = new Intent(MainActivity.this, NavigationActivity.class);
//                startActivity(intent);
//                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                //登录成功，关闭当前Activity
                saveUserInfo(Common.LOGIN_TYPE_NORMAL, null);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this, "账户密码错误", Toast.LENGTH_SHORT).show();
                clearInput();
            }
        });
    }
    private void updateUserInfo(Account user, PlatformDb data, final BmobUser.BmobThirdUserAuth authInfo) {
        Account newUser = new Account();
        newUser.setPhoto(data.getUserIcon());
        newUser.setSex("男".equals(data.getUserGender()) ? true : false);
        newUser.setUsername(data.getUserName());
        Account bmobUser = BmobUser.getCurrentUser(MainActivity.this, Account.class);
        newUser.update(MainActivity.this, bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this,R.string.update_userinfo_success,Toast.LENGTH_SHORT).show();
                //保存登录信息到本地
                saveUserInfo(Common.LOGIN_TYPE_THIRD, authInfo);
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this,R.string.update_userinfo_failed + msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveUserInfo(int loginType, BmobUser.BmobThirdUserAuth authInfo) {
        /*
         * TODO 把用户的登录信息保存到本地：sp\sqlite：（登录状态，登录类别，登录账户信息）
         * 注意:为了保证数据安全，一般对数据进行加密
         * 通过BmobUser user = BmobUser.getCurrentUser(context)获取登录成功后的本地用户信息
         * 如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(context,MyUser.class)获取自定义用户信息
         * */
        Account user = BmobUser.getCurrentUser(MainActivity.this, Account.class);
        PreferencesManager preferences = PreferencesManager.getInstance(MainActivity.this);
        preferences.put(Common.IS_LOGIN, true);
        preferences.put(Common.LOGINTYPE, loginType);
        preferences.put(Common.USER_NAME, user.getUsername());
        preferences.put(Common.USER_PHOTO, user.getPhoto());
        preferences.put(Common.USER_PWD, loginPassword.getText().toString());
        if(authInfo != null){
            preferences.put(authInfo);
        }
        MainActivity.this.finish();
    }

    private void clearInput() {
        loginName.setText("");
        loginPassword.setText("");
    }

}
