package com.example.wkmin.showmarker.cmn;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import com.example.wkmin.showmarker.data.House;

/**
 * Created by wkmin on 2017. 5. 12..
 *
 */

public class HouseClusterItem implements ClusterItem{

    private final LatLng mPosition;
    private final House house;
    private String iconUrl;

    public HouseClusterItem(double lat, double lng, House house) {
        this.mPosition = new LatLng(lat, lng);
        this.house = house;
        setIconUrl(house.getMarkerSmallBase());
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public House getHouse() {
        return house;
    }

    String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
