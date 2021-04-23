package com.google.developer.bugmaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.developer.bugmaster.Utils.FileUtils;
import com.google.developer.bugmaster.data.Insect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenwar on 29/07/2017.
 */

public class InsectServices {

    public static final String TAG = InsectServices.class.getSimpleName();

    public static ArrayList<Insect> getInsects(Context context) throws IOException{
        return getInsectsFromJSON(context);
    }

    private static ArrayList<Insect> getInsectsFromJSON(Context context) throws IOException{
        String json = FileUtils.loadJSONFromAsset(context);
        ArrayList<Insect> insectList = new ArrayList<Insect>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("insects");
            for (int i = 0; i < jsonArray.length(); i++ ){
                JSONObject jsonInsect = jsonArray.getJSONObject(i);
                Insect insect = new Insect(jsonInsect.optString("friendlyName"),
                        jsonInsect.optString("scientificName"),
                        jsonInsect.optString("classification"),
                        jsonInsect.optString("imageAsset"),
                        jsonInsect.optInt("dangerLevel"));
                insectList.add(insect);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return insectList;

    }

    public static void inserFakeData(SQLiteDatabase db){
        if (null == db){
            return;
        }

        List<ContentValues> list = new ArrayList<ContentValues>();

    }
}
