package com.example.wkmin.showmarker.map;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wkmin.showmarker.R;
import com.example.wkmin.showmarker.cmn.HouseClusterItem;
import com.example.wkmin.showmarker.cmn.HouseRenderer;
import com.example.wkmin.showmarker.data.House;
import com.example.wkmin.showmarker.data.source.HouseRepository;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Locale;

import io.realm.RealmResults;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback,
        MapsContract.View,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnCameraMoveListener,
        ClusterManager.OnClusterItemClickListener<HouseClusterItem> {

    // private final String TAG = getClass().getSimpleName();

    private GoogleMap mMap;
    private MapsContract.Presenter mPresenter;
    private HouseClusterItem clickedClusterItem;
    private ZoomPosition zoomPosition;
    private ClusterManager<HouseClusterItem> mClusterManager;
    private HouseRenderer houseRenderer;

    private enum ZoomPosition {
        BUILDING,
        STREET
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create Presenter
        new MapsPresenter(this, HouseRepository.getInstance());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnCameraMoveListener(this);
        mPresenter.addMarkerAll();
        setUpCluster();
    }

    private void setUpCluster() {
        mClusterManager = new ClusterManager<>(this, mMap);
        houseRenderer = new HouseRenderer(this, mMap, mClusterManager);
        mClusterManager.setRenderer(houseRenderer);
        mClusterManager.setOnClusterItemClickListener(this);
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mPresenter.addHouseCluster();
    }

    @Override
    public void setPresenter(MapsContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public Context getContext() {
        return this.getBaseContext();
    }

    @Override
    public void showMarkerAll(RealmResults<House> allHouse) {
        LatLng last = null;
        for (House house : allHouse) {
            last = new LatLng(house.getLat(), house.getLng());
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(last, 14));
        zoomPosition = ZoomPosition.STREET;
    }

    @Override
    public void addMarkerCluster(RealmResults<House> allHouse) {
        for (House house : allHouse) {
            HouseClusterItem houseClusterItem = new HouseClusterItem(house.getLat(), house.getLng(), house);
            mClusterManager.addItem(houseClusterItem);
        }
        mClusterManager.cluster();
    }

    @Override
    public boolean onClusterItemClick(HouseClusterItem houseClusterItem) {
        showDialogDetail(houseClusterItem.getHouse());
        changeUnSelectedMarkerIcon(clickedClusterItem);
        changeSelectedMarkerIcon(houseClusterItem);
        clickedClusterItem = houseClusterItem;
        // TODO : clickedClusterItem 위치가 애매함. (Network 속도에 따라 문제가 되지 않을까?)
        return false;
    }

    private void changeUnSelectedMarkerIcon(HouseClusterItem houseClusterItem) {
        if (houseClusterItem != null) {
            Marker marker = houseRenderer.getMarker(houseClusterItem);
            if (zoomPosition == ZoomPosition.STREET)
                loadImageMakerIcon(marker, houseClusterItem.getHouse().getMarkerSmallBase());
            else
                loadImageMakerIcon(marker, houseClusterItem.getHouse().getMarkerLargeBase());
        }
    }

    private void changeSelectedMarkerIcon(HouseClusterItem houseClusterItem ) {
        Marker marker = houseRenderer.getMarker(houseClusterItem);
        if (zoomPosition == ZoomPosition.STREET)
            loadImageMakerIcon(marker, houseClusterItem.getHouse().getMarkerSmallSelected());
        else
            loadImageMakerIcon(marker, houseClusterItem.getHouse().getMarkerLargeSelected());
    }

    private void showDialogDetail(House house) {
        LinearLayout houseDetailLayout = (LinearLayout) findViewById(R.id.houseDetailLayout);
        houseDetailLayout.setVisibility(View.VISIBLE);
        showHouseInfoDetailLayout(house);
    }

    private void showHouseInfoDetailLayout(House house) {
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_addr = (TextView) findViewById(R.id.tv_addr);
        TextView tv_price = (TextView) findViewById(R.id.tv_price);
        TextView tv_pricePerPyeong = (TextView) findViewById(R.id.tv_pricePerPyeong);

        final ImageView iv_image = (ImageView) findViewById(R.id.iv_image);

        final ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.loadImage(house.getImage(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                iv_image.setImageBitmap(loadedImage);
            }
        });

        tv_name.setText(house.getName());
        tv_addr.setText(house.getSido()+" "+
                        house.getGugun()+" "+
                        house.getDong()+" / "+
                        house.getHouseholds()+"세대 / "+
                        house.getBuildDate().substring(0,4)+"년 준공");
        String price = String.format(Locale.KOREA,
                "%.1f억/%.2f\u33A1 ~",house.getPrice()/10000.0, house.getFloorArea());
        tv_price.setText(price);
        String pricePerPyeong = String.format(Locale.KOREA,
                "%d만원 / 3.3\u33A1",
                house.getPrice()/(int)(house.getFloorArea()/3.3));
        tv_pricePerPyeong.setText(pricePerPyeong);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        LinearLayout houseDetailLayout = (LinearLayout) findViewById(R.id.houseDetailLayout);
        houseDetailLayout.setVisibility(View.GONE);
        changeUnSelectedMarkerIcon(clickedClusterItem);
    }

    @Override
    public void onCameraMove() {
        CameraPosition cameraPosition = mMap.getCameraPosition();
        if (cameraPosition.zoom > 17) { // divide size
            if (zoomPosition == ZoomPosition.STREET) {
                changeMarkerLargeIcon();
                zoomPosition = ZoomPosition.BUILDING;
            }
        } else {
            if (zoomPosition == ZoomPosition.BUILDING) {
                changeMarkerSmallIcon();
                zoomPosition = ZoomPosition.STREET;
            }
        }
    }

    private void changeMarkerLargeIcon() {
        for (HouseClusterItem item : mClusterManager.getAlgorithm().getItems()) {
            item.setIconUrl(item.getHouse().getMarkerLargeBase());
        }
    }

    private void changeMarkerSmallIcon() {
        for (HouseClusterItem item : mClusterManager.getAlgorithm().getItems()) {
            item.setIconUrl(item.getHouse().getMarkerSmallBase());
        }
    }

    // 마커 클러스터에 속해 있는 마커는 이벤트 처리시 작동으로 렌더링이 되지 않아서
    // 임시적으로 수정 Maker Icon을 변경함.
    private void loadImageMakerIcon(final Marker marker, String imageUrl) {
        final ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.loadImage(imageUrl, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(loadedImage));
            }
        });
    }

}