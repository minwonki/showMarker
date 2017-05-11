package com.example.wkmin.showmarker.map;

import android.util.Log;

import com.example.wkmin.showmarker.cmn.Util;
import com.example.wkmin.showmarker.data.House;
import com.example.wkmin.showmarker.data.source.HouseRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmResults;

/**
 * Created by wkmin on 2017. 5. 11..
 *
 */

public class MapsPresenter implements MapsContract.Presenter {

    private final String TAG = this.getClass().getName();

    private final MapsContract.View mMapsView;
    private final HouseRepository mHouseRepository;

    public MapsPresenter(MapsContract.View mapsView, HouseRepository houseRepository) {
        mMapsView = mapsView;
        mHouseRepository = houseRepository;
        this.mMapsView.setPresenter(this);
        loadJSONData();
    }

    @Override
    public void loadJSONData() {
        // TODO : JSON 파싱 및 DB에 저장
        String json = Util.readJSONFromAssets(mMapsView.getContext());
        ParsingJSON(json);
        RealmResults<House> houses =  mHouseRepository.findAllHouse();
    }

    private void ParsingJSON(String json) {
        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONArray filteredArray = jsonObj.getJSONArray("filtered");

            for (int i = 0 ; i < filteredArray.length() ; i++) {
                House house = new House();
                JSONObject houseObj = filteredArray.getJSONObject(i);
                house.setId(houseObj.getInt("id"));
                house.setName(houseObj.getString("name"));
                house.setLat(houseObj.getDouble("lat"));
                house.setLng(houseObj.getDouble("lng"));
                mHouseRepository.addHouse(house);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addMarkerAll() {
        mMapsView.showMarkerAll(mHouseRepository.findAllHouse());
    }
}
