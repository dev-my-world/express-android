package com.example.android.client.util;

import org.json.JSONArray;

/**
 * Created by zhang on 2017/6/24.
 */

public class ExpressJsonArrayResultUtil {

    private static JSONArray jsonArray;

    public static JSONArray getAllExpressInfo() throws Exception {
        String url = HttpUtil.BASE_URL + "allExpress";
        return new JSONArray(HttpUtil.getRequest(url));
    }
}
