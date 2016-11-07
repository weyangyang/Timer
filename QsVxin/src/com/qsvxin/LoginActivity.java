package com.qsvxin;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qsvxin.bean.LoginBean;
import com.qsvxin.engine.GetLoginData;
import com.qsvxin.net.interf.AfterLoginParserData;
import com.qsvxin.utils.CommonUtils;
import com.qsvxin.utils.Md5Util;
import com.qsvxin.utils.PreferenceUtils;
import com.qsvxin.utils.QsConstants;

public class LoginActivity extends Activity implements OnClickListener {
    private EditText edit_userName; // 帐号编辑框
    private EditText edit_passwd; // 密码编辑框
    private String str_userName;
    private String str_passwd;
    private Button btnLogin;
    private Button btnLoginBack;
    private AfterLoginParserData dataCallBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        afterLogin();
    }


    private void afterLogin() {
        dataCallBack = new AfterLoginParserData() {

            @Override
            public void getParserErrData(int errCode, String errMsg) {
                errorTips();
            }

            @Override
            public void getNetErrData(int errCode, String errMsg) {
                if (errCode == QsConstants.CLIENT_PROTOCOL_EXCEPTION) {
                    // TODO:提示网络连接超时
                    CommonUtils.errorTipsDialog(LoginActivity.this, LoginActivity.this.getString(R.string.str_prompt),
                            LoginActivity.this.getString(R.string.str_net_timeout));
                } else {
                    CommonUtils.errorTipsDialog(LoginActivity.this, LoginActivity.this.getString(R.string.str_prompt),
                            LoginActivity.this.getString(R.string.str_vmember_net_error));
                }
            }

            @Override
            public void getLoginSuccData(String succCode, String succMsg, final LoginBean mLoginBean) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainUIActivity.class);
                        startActivity(intent);
                        PreferenceUtils.setLoginAfPrefPram(LoginActivity.this, mLoginBean.getStrSecretKey(),
                                mLoginBean.getStrNickname(), mLoginBean.getStrToken(), mLoginBean.getStrAppid(),
                                mLoginBean.getStrAppsecret(), mLoginBean.getStrGh(), mLoginBean.getStrUserQQ(),
                                mLoginBean.getStrUserLastLoginTime(), mLoginBean.getStrUserRegisterTime(),
                                mLoginBean.getStrUserEmail(), mLoginBean.getStrUserPhoneNum(), true);
                        LoginActivity.this.finish();
                        if (WelcomeActivity.instance != null) {
                            WelcomeActivity.instance.finish();
                        }
                        CommonUtils.toast(getApplicationContext(), "登录成功");
                    }
                });

            }

            @Override
            public void getLoginErrData(String errCode, String errMsg) {
                errorTips();
            }
        };

    }

    private void init() {
        edit_userName = (EditText) findViewById(R.id.login_user_edit);
        edit_passwd = (EditText) findViewById(R.id.login_passwd_edit);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        btnLoginBack = (Button) findViewById(R.id.btnLoginBack);
        btnLoginBack.setOnClickListener(this);
    }

    private boolean checkInput(String strUserName, String strPasswd) {
        if (TextUtils.isEmpty(strUserName)) {
            CommonUtils.createDialog(this, R.drawable.login_error_icon, "警告", "用户名不能为空，请重新输入！");
            return false;
        } else if (TextUtils.isEmpty(strPasswd)) {
            CommonUtils.createDialog(this, R.drawable.login_error_icon, "警告", "密码不能为空，请重新输入！");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnLoginBack:
            this.finish();
            break;
        case R.id.btn_login:
            if (!CommonUtils.checkNet(LoginActivity.this)) {
                CommonUtils.createDialog(this, R.drawable.login_error_icon, "警告", "网络异常，请稍后再试！");
                return;
            }
            logining();
            break;
        }

    }

    private void logining() {
        str_userName = edit_userName.getText().toString();
        str_passwd = edit_passwd.getText().toString();
        if (checkInput(str_userName, str_passwd)) {
            str_passwd = Md5Util.getMD5Str(str_passwd);
             Map<String, String> mMap = new HashMap<String, String>();
             mMap.put("str_userName", str_userName);
             mMap.put("str_passwd", str_passwd);
            // TODO:未加入个推clientID
            String strClientID = PreferenceUtils.getPrefString(LoginActivity.this, "clientid", "");
             mMap.put("str_clientID", strClientID);
            // new LoginTask(this, mMap, dataCallBack).execute();
            new GetLoginData(this, mMap, dataCallBack, true).excute();
        }
    }

    private void errorTips() {
        LoginActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CommonUtils.createDialog(LoginActivity.this, R.drawable.login_error_icon, "登录失败", "用户名或密码错误，请重新输入！");
            }
        });
    }

}
