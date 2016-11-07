package com.qsvxin;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CommonUtils;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;

public class RegisterActivity extends Activity implements OnClickListener {
    private EditText edit_registerUser;
    private EditText edit_registerPasswd;
    private EditText edit_passwdAgain;
    private EditText edit_phoneNum;
    private EditText edit_registerEmail;
    private EditText edit_registerQQ;
    private Button btn_register;
    private Button btn_registerBack;
    private String strUserName;
    private String strPasswd;
    private String strResPasswd;
    private String strPhoneNum;
    private String strEmail;
    private String strQQNum;
    private NetDataCallBackInterf netDataCallBackInterf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        afterRegister();
    }
    private void afterRegister() {
        netDataCallBackInterf = new NetDataCallBackInterf() {
            
            @Override
            public void getNetErrData(int errCode, String strError) {
                //TODO:处理各种异信息
            }
            
            @Override
            public void getNetData(int statusCode, String strJson) {
                if(200==statusCode){
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RegisterActivity.this.finish();
                            CommonUtils.toast(getApplicationContext(), "注册成功");
                        }
                    });
                }else{
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CommonUtils.createDialog(RegisterActivity.this, R.drawable.login_error_icon, "注册失败", "请稍后再注册！");
                        }
                    });
                }
            }
        };
        
    }



    private void init() {
        edit_registerUser = (EditText) findViewById(R.id.edit_registerUser);
        edit_registerPasswd = (EditText) findViewById(R.id.edit_registerPasswd);
        edit_passwdAgain = (EditText) findViewById(R.id.edit_passwdAgain);
        edit_phoneNum = (EditText) findViewById(R.id.edit_phoneNum);
        edit_registerEmail = (EditText) findViewById(R.id.edit_registerEmail);
        edit_registerQQ = (EditText) findViewById(R.id.edit_registerQQ);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_registerBack = (Button) findViewById(R.id.btn_registerBack);
        btn_register.setOnClickListener(this);
        btn_registerBack.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_registerBack:
            // 返回上层界面
            this.finish();
            break;
        case R.id.btn_register:
            getInputMsg();
            boolean isNull = checkNull(strUserName, strPasswd, strResPasswd, strPhoneNum, strEmail, strQQNum);
            if (isNull) {
                CommonUtils.createDialog(this, R.drawable.login_error_icon, "警告", "注册信息不能为空，请重新输入！");
            } else {
                if(checkInputMsg()){
                    Map<String,String> mMap = new HashMap<String, String>();
                    mMap.put("str_userName", strUserName);
                    mMap.put("str_passwd", strPasswd);
                    mMap.put("str_phoneNum", strPhoneNum);
                    mMap.put("str_email", strEmail);
                    mMap.put("str_qqNum", strQQNum);
                    new RegisterTask(RegisterActivity.this, mMap,netDataCallBackInterf).execute();
                }
            }
            break;
        }
    }

    class RegisterTask extends QsAsyncTask {
    private Map<String, String> mMap;
    private NetDataCallBackInterf callBackInterf;
    private Context mContext;
    private CustomDialog mCustomDialog;

    public RegisterTask(Context mContext, Map<String, String> mMap, NetDataCallBackInterf netDataCallBack) {
        this.mMap = mMap;
        this.callBackInterf = netDataCallBackInterf;
        this.mContext = mContext;
    }

    @Override
    protected void onPreExectue() {
        mCustomDialog = CustomDialog.createLoadingDialog(mContext, "正在注册", true);
    }

    @Override
    protected void doInbackgroud() {
        RequestEngine.getInstance().register(mMap, callBackInterf);
    }
    @Override
    protected void onPostExecute() {
        mCustomDialog.dismiss();
    }
}
    /**
     * 检验注册输入信息
     * 
     * @return 正确时返回 true
     */
    private boolean checkInputMsg() {
        if (!CommonUtils.validUserName(strUserName)) {
            CommonUtils.createDialog(this, R.drawable.login_error_icon, "用户名错误", "请输入6-16位的字母或字母数字组合！");
            return false;
        } else if (!CommonUtils.validPassword(strPasswd)) {
            CommonUtils.createDialog(this, R.drawable.login_error_icon, "密码错误", "请输入6-16位的字母数字组合！");
            return false;
        } else if (!strPasswd.equals(strResPasswd)) {
            CommonUtils.createDialog(this, R.drawable.login_error_icon, "警告", "两次输入的密码不一致，请重新输入！");
            return false;
        } else if (!CommonUtils.validMobile(strPhoneNum)) {
            CommonUtils.createDialog(this, R.drawable.login_error_icon, "手机号有误", "您输入的手机号不正确，请重新输入！");
            return false;
        } else if (!CommonUtils.isEmailAddress(strEmail)) {
            CommonUtils.createDialog(this, R.drawable.login_error_icon, "邮箱账号有误", "您输入的邮箱账号不正确，请重新输入！");
            return false;
        } else if (!CommonUtils.validQQNum(strQQNum)) {
            CommonUtils.createDialog(this, R.drawable.login_error_icon, "QQ号有误", "请输入5-11位QQ号，请重新输入！");
            return false;
        }
        return true;

    }

    /**
     * 获取输入信息
     */
    private void getInputMsg() {
        strUserName = edit_registerUser.getText().toString();
        strPasswd = edit_registerPasswd.getText().toString();
        strResPasswd = edit_passwdAgain.getText().toString();
        strPhoneNum = edit_phoneNum.getText().toString();
        strEmail = edit_registerEmail.getText().toString();
        strQQNum = edit_registerQQ.getText().toString();
    }

    /**
     * 检测是否为空
     * 
     * @param strUserName
     * @param strPasswd
     * @param strResPasswd
     * @param strPhoneNum
     * @param strEmail
     * @param strQQNum
     * @return 不为空时返回true
     */
    private boolean checkNull(String strUserName, String strPasswd, String strResPasswd, String strPhoneNum,
            String strEmail, String strQQNum) {
        if (TextUtils.isEmpty(strUserName) || TextUtils.isEmpty(strPasswd) || TextUtils.isEmpty(strResPasswd)
                || TextUtils.isEmpty(strPhoneNum) || TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strQQNum)) {
            return true;
        } else {
            return false;
        }

    }

}
