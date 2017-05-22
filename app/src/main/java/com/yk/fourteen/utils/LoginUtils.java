package com.yk.fourteen.utils;

import android.content.Intent;
import android.widget.Toast;

import com.yk.fourteen.R;
import com.yk.fourteen.bean.Account;
import com.yk.fourteen.common.Common;
import com.yk.fourteen.common.PreferencesManager;

import org.json.JSONObject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.SaveListener;


public class LoginUtils {

    public static boolean isLogin(){
        return PreferencesManager.getInstance(BaseApplication.getInstance()).get(Common.IS_LOGIN,false);
    }

    public static void checkLogin(boolean needLogin){
        if(isLogin()){
            Account account = BmobUser.getCurrentUser(BaseApplication.getInstance(),Account.class);
            if(account == null){
                //保存了登录信息还没有登录就自动登录，用于初始化时
                autoLogin();
            }
        }else if(needLogin){
            //需要检查登录状态，没有登录的话跳转到LoginActivity
            jump2Login();
        }
    }

    private static void jump2Login(){
//        BaseApplication.getInstance().startActivity(new Intent(BaseApplication.getInstance(), LoginActivity.class));
        BaseApplication.getInstance().startActivity(new Intent("com.yk.fourteen.MainActivity").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public static void autoLogin(){
        String userPhoto = PreferencesManager.getInstance(BaseApplication.getInstance()).get(Common.USER_PHOTO);
        String userName = PreferencesManager.getInstance(BaseApplication.getInstance()).get(Common.USER_NAME);
        String userPwd = PreferencesManager.getInstance(BaseApplication.getInstance()).get(Common.USER_PWD);
        BmobUser.BmobThirdUserAuth authInfo = (BmobUser.BmobThirdUserAuth) PreferencesManager.getInstance(BaseApplication.getInstance()).get(BmobUser.BmobThirdUserAuth.class);
        int loginType = PreferencesManager.getInstance(BaseApplication.getInstance()).get(Common.LOGINTYPE,0);
        switch (loginType){
            case Common.LOGIN_TYPE_NORMAL:
                loginByUser(userName,userPwd);
                break;
            case Common.LOGIN_TYPE_THIRD:
                loginByThird(authInfo);
                break;
            default:
                Toast.makeText(BaseApplication.getInstance(), R.string.auto_login_failed,Toast.LENGTH_SHORT);
                break;
        }
    }

    public static void loginByThird(BmobUser.BmobThirdUserAuth authInfo) {
        BmobUser.loginWithAuthData(BaseApplication.getInstance(), authInfo, new OtherLoginListener() {

            @Override
            public void onSuccess(JSONObject userAuth) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(BaseApplication.getInstance(), R.string.auto_login_third_failed + msg,Toast.LENGTH_SHORT);
            }
        });
    }

    public static void loginByUser(String userName, final String userPwd) {
        //使用BmobSDK提供的登录功能
        Account account = new Account();
        account.setUsername(userName);
        account.setPassword(userPwd);
        account.login(BaseApplication.getInstance(), new SaveListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(BaseApplication.getInstance(), s,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
