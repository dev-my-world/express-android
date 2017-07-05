package com.example.android.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by zhang on 2017/7/5.
 */


public class ActivityWebView1 extends AppCompatActivity {
    private WebView mWebview;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_webview);


        mWebview = (WebView) findViewById(R.id.webView1);

        mWebSettings = mWebview.getSettings();

        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setBlockNetworkImage(true);
      //  mWebSettings.setLoadsImagesAutomatically(true);


        int intExtra = getIntent().getIntExtra("choose", -1);

        switch (intExtra) {
            case 1:
                mWebview.loadUrl("https://pro.m.jd.com/mall/active/2hqsQcyM5bEUVSStkN3BwrBHqVLd/index.html");
                break;
            case 2:
                mWebview.loadUrl("https://pro.m.jd.com/mall/active/2hqsQcyM5bEUVSStkN3BwrBHqVLd/index.html");
                break;
            case 3:
                mWebview.loadUrl("https://www.amazon.cn/亚马逊海外购/b/ref=nav_nav_topnav_ags?ie=UTF8&node=1403206071");
                break;
            default:
                mWebview.loadUrl("https://www.amazon.cn/亚马逊海外购/b/ref=nav_nav_topnav_ags?ie=UTF8&node=1403206071");
                break;

        }


        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {


            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                System.out.println("标题在这里");
                setTitle(title);
            }


            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                }
            }
        });


        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                System.out.println("开始加载了");

            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                mWebSettings.setBlockNetworkImage(false);
            }
        });
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }
}
