package com.yk.fourteen.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yk.fourteen.R;
import com.yk.fourteen.common.Common;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.register_et_name)
    EditText registerEtName;
    @BindView(R.id.textInputLayout3)
    TextInputLayout textInputLayout3;
    @BindView(R.id.register_et_pwd)
    EditText registerEtPwd;
    @BindView(R.id.textInputLayout4)
    TextInputLayout textInputLayout4;
    @BindView(R.id.register_btn)
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        //Bmob ID
        Bmob.initialize(this, Common.BMOB_ID);
    }

    @OnClick(R.id.register_btn)
    public void onViewClicked() {
        EndRegister();
    }

    private void EndRegister() {
        final String registerName = registerEtName.getText().toString();
        final String registerPwd = registerEtPwd.getText().toString();
        BmobUser user = new BmobUser();
        user.setUsername(registerName);
        user.setPassword(registerPwd);
        if (registerName.equals("") || registerPwd.equals("")){
            Toast.makeText(RegisterActivity.this,"账户和密码不能为空",Toast.LENGTH_SHORT).show();
        }else if (registerName.length()<6 ||    registerPwd.length()<6){
            Toast.makeText(RegisterActivity.this,"账户和密码不能小于6位数",Toast.LENGTH_SHORT).show();
        }else {
            user.signUp(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
