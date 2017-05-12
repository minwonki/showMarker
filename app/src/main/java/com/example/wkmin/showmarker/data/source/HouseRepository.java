package com.example.wkmin.showmarker.data.source;

import com.example.wkmin.showmarker.data.House;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by wkmin on 2017. 5. 11..
 *
 */

public class HouseRepository {
    private static HouseRepository INSTANCE = null;
    private final Realm realm;

    private HouseRepository() {
        // TODO : 마이그레이션 작업 필요. 현재는 잦은 모델 변화로 적용 안함.
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();
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

    private void addHouse(final House house) {
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

    public void ParsingJSONtoRepository(String json) {
        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONArray filteredArray = jsonObj.getJSONArray("filtered");

            for (int i = 0 ; i < filteredArray.length() ; i++) {
                House house = new House();
                JSONObject houseObj = filteredArray.getJSONObject(i);

                house.setId(houseObj.getInt("id"));

                house.setName(houseObj.getString("name"));
                house.setSido(houseObj.getString("sido"));
                house.setGugun(houseObj.getString("gugun"));
                house.setDong(houseObj.getString("dong"));
                house.setBunji(houseObj.getString("bunji"));

                house.setLat(houseObj.getDouble("lat"));
                house.setLng(houseObj.getDouble("lng"));

                house.setHouseholds(houseObj.getInt("households"));
                house.setBuildDate(houseObj.getString("buildDate"));
                house.setScore(houseObj.getInt("score"));

                house.setBrand(houseObj.getString("brand"));
                house.setImage(houseObj.getString("image"));

                house.setPrice(houseObj.getInt("price"));
                house.setFloorArea(houseObj.getDouble("floorArea"));

                house.setMarkerSmallBase(houseObj
                        .getJSONObject("marker")
                        .getJSONObject("small")
                        .getJSONObject("xxxh").getString("base"));
                house.setMarkerSmallSelected(houseObj
                        .getJSONObject("marker")
                        .getJSONObject("small")
                        .getJSONObject("xxxh").getString("selected"));
                house.setMarkerLargeBase(houseObj
                        .getJSONObject("marker")
                        .getJSONObject("large")
                        .getJSONObject("xxxh").getString("base"));
                house.setMarkerLargeSelected(houseObj
                        .getJSONObject("marker")
                        .getJSONObject("large")
                        .getJSONObject("xxxh").getString("selected"));
                this.addHouse(house);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
