package com.qsvxin.net.interf;

import java.util.List;

import org.apache.http.NameValuePair;

public interface Request2ServiceInterf {
    // public void Request2Service(Map<String, Object> mMap,
    // NetDataCallBackInterf netDataCallBackInterf)
    // throws UnsupportedEncodingException, ClientProtocolException,
    // IOException;

    public void Request2Service(String strUrl, List<NameValuePair> nameValuePairs,
            NetDataCallBackInterf netDataCallBackInterf);

    public void Request2Service(String strUrl, NetDataCallBackInterf netDataCallBackInterf, NameValuePair... params);
}
