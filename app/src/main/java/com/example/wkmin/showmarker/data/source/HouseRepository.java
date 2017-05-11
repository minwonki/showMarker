package com.example.wkmin.showmarker.data.source;

import com.example.wkmin.showmarker.data.House;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by wkmin on 2017. 5. 11..
 *
 */

public class HouseRepository {
    private static HouseRepository INSTANCE = null;
    private final Realm realm;

    public HouseRepository() {
        realm = Realm.getDefaultInstance();
        // TODO : 마이그레이션 작업 필요.
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

    public void addHouse(final House house) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(house);
            }
        });
    }

    public RealmResults<House> findAllHouse() {
        return realm.where(House.class).findAll();
    }
}
