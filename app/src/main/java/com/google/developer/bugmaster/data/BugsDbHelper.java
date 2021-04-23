package com.google.developer.bugmaster.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.developer.bugmaster.R;
import com.google.developer.bugmaster.data.InsectContract.InsectEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Database helper class to facilitate creating and updating
 * the database from the chosen schema.
 */
public class BugsDbHelper extends SQLiteOpenHelper {
    private static final String TAG = BugsDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "insects.db";
    private static final int DATABASE_VERSION = 1;

    //Used to read data from res/ and assets/
    private Resources mResources;

    public BugsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO: Create and fill the database
        final String SQL_CREATE_TABLE_INSECT =
                "CREATE TABLE " + InsectEntry.TABLE_NAME +  " (" +
                        InsectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        InsectEntry.COLUMN_FRIENDLY_NAME + " TEXT, " +
                        InsectEntry.COLUMN_SCIENTIFIC_NAME + " TEXT, " +
                        InsectEntry.COLUMN_CLASSIFICATION + " TEXT, " +
                        InsectEntry.COLUMN_IMAGE + " TEXT, " +
                        InsectEntry.COLUMN_DANGER_LEVEL + " INTEGER " + ");";

        db.execSQL(SQL_CREATE_TABLE_INSECT);
        Log.d(TAG, "Crete Table success!");

        try {
            readInsectsFromResources(db);
            Log.d(TAG, "Insert data success");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Handle database version upgrades
        final String SQL_DELETE_INSECTS = "DROP TABLE IF EXISTS " + InsectEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_INSECTS);
        onCreate(db);
    }

    /**
     * Streams the JSON data from insect.json, parses it, and inserts it into the
     * provided {@link SQLiteDatabase}.
     *
     * @param db Database where objects should be inserted.
     * @throws IOException
     * @throws JSONException
     */
    private void readInsectsFromResources(SQLiteDatabase db) throws IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        InputStream in = mResources.openRawResource(R.raw.insects);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        //Parse resource into key/values
        final String rawJson = builder.toString();
        //TODO: Parse JSON data and insert into the provided database instance

        JSONObject jsonObject = new JSONObject(rawJson);
        JSONArray jsonArray = jsonObject.getJSONArray("insects");
        List<ContentValues> values = new ArrayList<ContentValues>();
        for (int i = 0; i < jsonArray.length(); i++ ){
            JSONObject jsonInsect = jsonArray.getJSONObject(i);
            ContentValues value = new ContentValues();
            value.put(InsectEntry.COLUMN_FRIENDLY_NAME, jsonInsect.optString("friendlyName"));
            value.put(InsectEntry.COLUMN_SCIENTIFIC_NAME, jsonInsect.optString("scientificName"));
            value.put(InsectEntry.COLUMN_CLASSIFICATION, jsonInsect.optString("classification"));
            value.put(InsectEntry.COLUMN_IMAGE, jsonInsect.optString("imageAsset"));
            value.put(InsectEntry.COLUMN_DANGER_LEVEL, jsonInsect.optInt("dangerLevel"));

            values.add(value);
        }

        try {
            for (ContentValues c: values){
                db.insert(InsectEntry.TABLE_NAME, null, c);
                Log.d(TAG, "Insert Insect: " + c.get(InsectEntry.COLUMN_FRIENDLY_NAME));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
