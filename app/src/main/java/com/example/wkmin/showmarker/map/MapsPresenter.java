package com.example.wkmin.showmarker.map;

import com.example.wkmin.showmarker.cmn.Util;
import com.example.wkmin.showmarker.data.source.HouseRepository;

/**
 * Created by wkmin on 2017. 5. 11..
 *
 */

class MapsPresenter implements MapsContract.Presenter {

    private final String TAG = this.getClass().getName();

    private final MapsContract.View mMapsView;
    private final HouseRepository mHouseRepository;

    MapsPresenter(MapsContract.View mapsView, HouseRepository houseRepository) {
        mMapsView = mapsView;
        mHouseRepository = houseRepository;
        this.mMapsView.setPresenter(this);
        loadJSONData();
    }

    @Override
    public void loadJSONData() {
        String json = Util.readJSONFromAssets(mMapsView.getContext());
        mHouseRepository.ParsingJSONtoRepository(json);
    }

    @Override
    public void addMarkerAll() {
        mMapsView.showMarkerAll(mHouseRepository.findAllHouse());
    }

    @Override
    public void addHouseCluster() {
        mMapsView.addMarkerCluster(mHouseRepository.findAllHouse());
    }
}
