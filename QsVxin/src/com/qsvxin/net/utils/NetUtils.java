package com.qsvxin.net.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.utils.QsConstants;

public class NetUtils {

    private static final String CHAR_SET = HTTP.UTF_8;
    private static HttpClient mHttpClient;

    private NetUtils() {

    }

    public synchronized static HttpClient getHttpClient() {
        if (null == mHttpClient) {
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setContentCharset(params, CHAR_SET);
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setUseExpectContinue(params, true);
            // timeout setting
            /*
             * This defines the connection pool management from
             * ConnectionManager remove the connection timeout.
             */
            ConnManagerParams.setTimeout(params, 30000);
            /*
             * This defines the connection with the server via a network
             * timeout. Httpclient package via an asynchronous thread to create
             * a socket connection with the server, which is connected to the
             * socket timeout.
             */
            HttpConnectionParams.setConnectionTimeout(params, 30000);

            /*
             * This defines the Socket read data timeout, which fetch response
             * data from the server time to wait.
             */
            HttpConnectionParams.setSoTimeout(params, 30000);

            // Set our HttpClient supports HTTP and HTTPS mode.
            SchemeRegistry schReg = new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
            // Use thread-safe connection manager to create HttpClient
            ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
            mHttpClient = new DefaultHttpClient(conMgr, params);
        }

        return mHttpClient;

    }

    public static boolean getDownload(String url, String path) throws ClientProtocolException, IOException {
        HttpResponse response;
        HttpGet mHttpGet = new HttpGet(url);
        mHttpGet.addHeader("Accept-Language", "zh-CN");
        HttpClient client = getHttpClient();
        response = client.execute(mHttpGet);
        int statusCode = response.getStatusLine().getStatusCode();

        /*
         * HttpEntity resEntity = response.getEntity(); return (resEntity ==
         * null) ? "" : EntityUtils.toString(resEntity, CHAR_SET);
         */
        if (statusCode == 200) {
            InputStream is = response.getEntity().getContent();
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);

            byte[] temp = new byte[10 * 1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();
            return true;
        }
        return false;
    }

    /**
     * 解压 Gzip
     * 
     * @param b
     * @return
     * @throws Exception
     */
    private static byte[] unzip(byte[] b) throws Exception {
        if (b == null)
            return null;
        if (b.length > 512 * 1024)
            return null;
        byte[] unzipdata = null;

        ByteArrayInputStream bais = new ByteArrayInputStream(b);

        GZIPInputStream gzin = new GZIPInputStream(bais);

        byte[] buffer = new byte[1024 * 4];

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        int ch;

        while ((ch = gzin.read(buffer)) != -1) {
            dos.write(buffer, 0, ch);
        }
        unzipdata = baos.toByteArray();

        dos.close();
        baos.close();

        buffer = null;
        gzin.close();
        bais.close();
        return unzipdata;
    }

    private static byte[] readBytes(InputStream in, final int length) throws IOException {
        OutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 20];
        int n = 0, size = 0;

        while ((n = in.read(buffer)) > 0) {
            size += n;
            os.write(buffer, 0, n);
        }
        buffer = null;

        if (-1 != length && size != length)
            return null;

        byte[] bs = ((ByteArrayOutputStream) os).toByteArray();
        os.close();
        return bs;
    }

    public static String connectNet2service(String strUrl, List<NameValuePair> nameValuePairs,
            NetDataCallBackInterf netDataCallBackInterf) {

        String strJson = "";
        HttpClient client = getHttpClient();
        HttpPost httpPost = new HttpPost(strUrl);
        HttpResponse response = null;

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
            response = client.execute(httpPost);
            strJson = getNetSuccData(netDataCallBackInterf, strJson, response);
        } catch (UnsupportedEncodingException e) {
            netDataCallBackInterf.getNetErrData(QsConstants.UNSUPPORTED_ENCODING_EXCEPTION, e.getMessage());
        } catch (ClientProtocolException e) {
            netDataCallBackInterf.getNetErrData(QsConstants.CLIENT_PROTOCOL_EXCEPTION, e.getMessage());
		} catch (IOException e) {
            netDataCallBackInterf.getNetErrData(QsConstants.IO_EXCEPTION, e.getMessage());
		} catch(Exception e) {
            netDataCallBackInterf.getNetErrData(QsConstants.EXEPTION, e.getMessage());
		}
        return strJson;
    }

    private static String getNetSuccData(NetDataCallBackInterf netDataCallBackInterf, String strJson,
            HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            try {
                byte[] bytesResult = null;
                // 读出下行 Byte Array
                bytesResult = readBytes(entity.getContent(), (int) entity.getContentLength());
                // 尝试解压两次
                for (int i = 1; i <= 2; i++) {
                    try {
                        bytesResult = unzip(bytesResult);
                    } catch (Exception e) {
                        break;
                    }
                }
                strJson = new String(bytesResult);
            } catch (Exception e) {
                // readBytes 不成功，就按照原有的方式读出
                netDataCallBackInterf.getNetErrData(QsConstants.EXEPTION, e.getMessage());
                strJson = EntityUtils.toString(entity, "UTF-8");
            }
            if (netDataCallBackInterf != null) {
                netDataCallBackInterf.getNetData(statusCode, strJson);
            }
        } else {
            if (netDataCallBackInterf != null) {
                netDataCallBackInterf.getNetData(statusCode, strJson);
            }
        }
        return strJson;
    }

    public static String connectNet2service(String strUrl, NetDataCallBackInterf netDataCallBackInterf,
            NameValuePair... params) {
        String strJson = "";
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        if (params != null) {
        	for (NameValuePair p : params) {
        		formparams.add(p);
        	}
        }
        UrlEncodedFormEntity entity = null;
        HttpPost request = new HttpPost(strUrl);
        HttpClient client = getHttpClient();
        HttpResponse response = null;
        try {
            entity = new UrlEncodedFormEntity(formparams, CHAR_SET);
            request.setEntity(entity);
            response = client.execute(request);
            strJson = getNetSuccData(netDataCallBackInterf, strJson, response);
        } catch (UnsupportedEncodingException e1) {
            netDataCallBackInterf.getNetErrData(QsConstants.UNSUPPORTED_ENCODING_EXCEPTION, e1.getMessage());
        } catch (ClientProtocolException e) {
        	netDataCallBackInterf.getNetErrData(QsConstants.CLIENT_PROTOCOL_EXCEPTION, e.getMessage());
		} catch (IOException e) {
			netDataCallBackInterf.getNetErrData(QsConstants.IO_EXCEPTION, e.getMessage());
		} catch(Exception e) {
            netDataCallBackInterf.getNetErrData(QsConstants.EXEPTION, e.getMessage());
		}
        return strJson;
    }
    
  //第一种方法
    public Bitmap getHttpBitmap(String strUrl) throws IOException
    {
        Bitmap bitmap = null;
            //初始化一个URL对象
            URL url = new URL(strUrl);
            //获得HTTPConnection网络连接对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5*1000);
            connection.setDoInput(true);
            connection.connect();
            //得到输入流
            InputStream is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            connection.disconnect();
        return bitmap;
    }
    
    //第二种方法
    public static Bitmap getBitmap(String strUrl) throws IOException
    {
        Bitmap bitmap = null;
            URL url = new URL(strUrl);
            bitmap = BitmapFactory.decodeStream(url.openStream());
        return bitmap;
    }

}
