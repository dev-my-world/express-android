package com.example.android.client.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by zhang on 2017/6/19.
 */

public class HttpUtil {
    //创建Http对象
    public static HttpClient httpClient = new DefaultHttpClient();
    public static final String BASE_URL = "http://192.168.31.58:8088/android/";

    /**
     * @param url 发送请求的url
     * @return 服务器相应字符串
     * @throws Exception
     */

    public static String getRequest(final String url) throws Exception {
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                HttpGet get = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(get);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    return EntityUtils.toString(httpResponse.getEntity());
                }
                return null;
            }
        });

        new Thread(task).run();
        return task.get();


    }


    /**
     *
     */
    public static String postRequest(final String url, final Map<String, String> rawParams) throws Exception {
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                HttpPost post = new HttpPost(url);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                for (String key : rawParams.keySet()) {
                    params.add(new BasicNameValuePair(key, rawParams.get(key)));
                }
                post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));


                HttpResponse httpResponse = httpClient.execute(post);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    return EntityUtils.toString(httpResponse.getEntity());
                }
                return null;
            }
        });
        new Thread(futureTask).run();
        return futureTask.get();
    }

}
