package com.example.wkmin.showmarker.data.source;

/**
 * Created by wkmin on 2017. 5. 11..
 *
 */

public class HouseRepository {
    private static HouseRepository INSTANCE = null;

    public HouseRepository() {
    }

    public static HouseRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HouseRepository();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
