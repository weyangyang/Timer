package com.qsvxin.net.utils;

import java.util.List;

import org.apache.http.NameValuePair;

import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.interf.Request2ServiceInterf;

public abstract class BaseRequest implements Request2ServiceInterf {

    public void Request2Service(String strUrl, List<NameValuePair> nameValuePairs,
            NetDataCallBackInterf netDataCallBackInterf)  {
        NetUtils.connectNet2service(strUrl, nameValuePairs, netDataCallBackInterf);
    }

    public void Request2Service(String strUrl, NetDataCallBackInterf netDataCallBackInterf, NameValuePair... params)
          {
        NetUtils.connectNet2service(strUrl, netDataCallBackInterf, params);
    }

}
