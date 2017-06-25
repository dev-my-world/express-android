package com.example.android.client.util;

/**
 * Created by zhang on 2017/6/24.
 */

public class StationNames {
    private static String[] stations = {"菜鸟驿站", "I DO(___)", "联通复印店", "地瓜坊", "青春修炼营"};

    public static String getStationName(int id) {
        return stations[id];
    }
}
