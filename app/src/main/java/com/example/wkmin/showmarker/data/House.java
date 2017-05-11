package com.example.wkmin.showmarker.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by wkmin on 2017. 5. 11..
 * House Model
 */

public class House extends RealmObject {

    private String  name;
    private String  sido;
    private String  gugun;
    private String  dong;
    private String  bunji;

    private double  lat;
    private double  lng;

    private int     households;
    private int     buildDate;
    private int     score;

    private String  brand;
    private String  image;

    private int     price;
    private double  floorArea;

    private String  markerSmallBase;
    private String  markerSmallSelected;

    private String  markerLargeBase;
    private String  markerLargeSelected;

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

    public String getSido() {
        return sido;
    }

    public void setSido(String sido) {
        this.sido = sido;
    }

    public String getGugun() {
        return gugun;
    }

    public void setGugun(String gugun) {
        this.gugun = gugun;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public String getBunji() {
        return bunji;
    }

    public void setBunji(String bunji) {
        this.bunji = bunji;
    }

    public int getHouseholds() {
        return households;
    }

    public void setHouseholds(int households) {
        this.households = households;
    }

    public int getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(int buildDate) {
        this.buildDate = buildDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(double floorArea) {
        this.floorArea = floorArea;
    }

    public String getMarkerSmallBase() {
        return markerSmallBase;
    }

    public void setMarkerSmallBase(String markerSmallBase) {
        this.markerSmallBase = markerSmallBase;
    }

    public String getMarkerSmallSelected() {
        return markerSmallSelected;
    }

    public void setMarkerSmallSelected(String markerSmallSelected) {
        this.markerSmallSelected = markerSmallSelected;
    }

    public String getMarkerLargeBase() {
        return markerLargeBase;
    }

    public void setMarkerLargeBase(String markerLargeBase) {
        this.markerLargeBase = markerLargeBase;
    }

    public String getMarkerLargeSelected() {
        return markerLargeSelected;
    }

    public void setMarkerLargeSelected(String markerLargeSelected) {
        this.markerLargeSelected = markerLargeSelected;
    }
}
