package com.example.wkmin.showmarker.map;

import android.content.Context;

import com.example.wkmin.showmarker.BasePresenter;
import com.example.wkmin.showmarker.BaseView;
import com.example.wkmin.showmarker.data.House;

import io.realm.RealmResults;

/**
 * Created by wkmin on 2017. 5. 11..
 *
 */

class MapsContract {
    interface View extends BaseView<Presenter> {
        void showDetailHouseInfo();

        Context getContext();
        void showMarkerAll(RealmResults<House> allHouse);
    }

    interface Presenter extends BasePresenter {
        void addMarkerAll();
    }
}
