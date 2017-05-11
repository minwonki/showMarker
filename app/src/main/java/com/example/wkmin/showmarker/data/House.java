package com.example.wkmin.showmarker.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by wkmin on 2017. 5. 11..
 *
 */

public class House  extends RealmObject {

    private String  name;
    private double  lat;
    private double  lng;


    @PrimaryKey
    private int      id;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLng() {
        return lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }
}
