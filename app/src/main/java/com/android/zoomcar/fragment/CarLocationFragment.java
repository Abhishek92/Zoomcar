package com.android.zoomcar.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarLocationFragment extends SupportMapFragment implements OnMapReadyCallback {


    private Double mLatitude;
    private Double mLongitude;
    private String mCarName;
    public CarLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLatitude = getArguments().getDouble("latitude");
        mLongitude = getArguments().getDouble("longitude");
        mCarName = getArguments().getString("name");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = super.onCreateView(inflater,container,savedInstanceState);
        getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng sydney = new LatLng(mLatitude, mLongitude);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title(mCarName)
                .snippet("Latitude is: "+mLatitude+" "+"Longitude is: "+mLongitude)
                .position(sydney));
    }
}
