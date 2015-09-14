package com.android.zoomcar.api;

import retrofit.RestAdapter;

/**
 * Created by hp pc on 11-07-2015.
 */
public class ZoomCarApiClient {

    private final static String API_URL = "http://zoomcar.0x10.info";
    private static ZoomCarApiInterface zoomCarApiInterface;

    private static RestAdapter getRestAdapter()
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return restAdapter;
    }

    public static ZoomCarApiInterface getCarsApi() {
        if (zoomCarApiInterface == null)
            zoomCarApiInterface = getRestAdapter().create(ZoomCarApiInterface.class);

        return zoomCarApiInterface;
    }
}
