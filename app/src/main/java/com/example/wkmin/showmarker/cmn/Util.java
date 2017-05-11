package com.example.wkmin.showmarker.cmn;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wkmin on 2017. 5. 11..
 *
 */

public class Util {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String readJSONFromAssets(Context context) {

        String json = null;
        try {
            InputStream is = context.getAssets().open("mobile.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return json;
    }
}
