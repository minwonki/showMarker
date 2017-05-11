package com.example.wkmin.showmarker.map;

/**
 * Created by wkmin on 2017. 5. 11..
 *
 */

public class MapsPresenter implements MapsContract.Presenter {

    private final MapsContract.View mMapsView;

    public MapsPresenter(MapsContract.View mapsView) {
        mMapsView = mapsView;
        this.mMapsView.setPresenter(this);
    }

    @Override
    public void loadJSONData() {
        // TODO : JSON 파싱 및 DB에 저장
    }

    @Override
    public void addMarkerAll() {
        mMapsView.showMarkerAll();
    }
}
