package com.example.wkmin.showmarker.map;

import com.example.wkmin.showmarker.BasePresenter;
import com.example.wkmin.showmarker.BaseView;

/**
 * Created by wkmin on 2017. 5. 11..
 *
 */

class MapsContract {
    interface View extends BaseView<Presenter> {
        void showDetailHouseInfo();
        void showMarkerAll();
    }

    interface Presenter extends BasePresenter {
        void addMarkerAll();
    }
}
