package com.example.wkmin.showmarker.map;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.wkmin.showmarker.R;
import com.example.wkmin.showmarker.data.House;
import com.example.wkmin.showmarker.data.source.HouseRepository;

import io.realm.RealmResults;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, MapsContract.View, GoogleMap.OnMapClickListener {

    private final String TAG = getClass().getName();

    private GoogleMap mMap;
    private MapsContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // create Presenter
        new MapsPresenter(this, new HouseRepository());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        mPresenter.addMarkerAll();
    }

    @Override
    public void setPresenter(MapsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showDetailHouseInfo() {
        // TODO : 선택한 마커의 상세 정보 표시
    }

    @Override
    public Context getContext() {
        return this.getBaseContext();
    }

    @Override
    public void showMarkerAll(RealmResults<House> allHouse) {
        LatLng last = null;
        // TODO : 모든 마커 표시
        for (House house:allHouse) {
            LatLng latlng = new LatLng(house.getLat(), house.getLng());
            Marker marker = mMap.addMarker(new MarkerOptions().position(latlng));
            marker.setTag(house);
            last = latlng;
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(last, 15));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        showDialogDetail(marker.getTag());
        return false;
    }

    private void showDialogDetail(Object tag) {
        Log.i(TAG, "marker name:"+((House)tag).getName());
        LinearLayout houseDetailLayout = (LinearLayout) findViewById(R.id.houseDetailLayout);
        houseDetailLayout.setVisibility(View.VISIBLE);
        showHouseInfoDetailLayout((House)tag);
    }

    private void showHouseInfoDetailLayout(House house) {
        // TODO : Image, 각종 정보들 배치하기
    }

    @Override
    public void onMapClick(LatLng latLng) {
        LinearLayout houseDetailLayout = (LinearLayout) findViewById(R.id.houseDetailLayout);
        houseDetailLayout.setVisibility(View.GONE);
    }
}
