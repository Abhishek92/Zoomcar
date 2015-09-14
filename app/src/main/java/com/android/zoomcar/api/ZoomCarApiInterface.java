package com.android.zoomcar.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by hp pc on 11-07-2015.
 */
public interface ZoomCarApiInterface {

    @GET("/api/zoomcar?type=json&query=list_cars")
    void getCarsDetail(Callback<CarDetailsModel> carDetailModelCallback);
    @GET("/api/zoomcar?type=json&query=api_hits")
    void getApiCounter(Callback<CarApiHitModel> carApiHitModelCallback);
}
