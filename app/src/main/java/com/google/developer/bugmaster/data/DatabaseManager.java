package com.google.developer.bugmaster.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.developer.bugmaster.data.InsectContract.InsectEntry;

/**
 * Singleton that controls access to the SQLiteDatabase instance
 * for this application.
 */
public class DatabaseManager {
    private static DatabaseManager sInstance;

    public static synchronized DatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context.getApplicationContext());
        }

        return sInstance;
    }

    private BugsDbHelper mBugsDbHelper;

    private DatabaseManager(Context context) {
        mBugsDbHelper = new BugsDbHelper(context);
    }

    /**
     * Return a {@link Cursor} that contains every insect in the database.
     *
     * @param sortOrder Optional sort order string for the query, can be null
     * @return {@link Cursor} containing all insect results.
     */
    public Cursor queryAllInsects(String sortOrder) {
        //TODO: Implement the query
        Cursor cursor = null;
        try {
            SQLiteDatabase db = mBugsDbHelper.getReadableDatabase();
            cursor = db.query(InsectEntry.TABLE_NAME, null, null, null, null,
                    null, sortOrder);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursor;
    }

    /**
     * Return a {@link Cursor} that contains a single insect for the given unique id.
     *
     * @param id Unique identifier for the insect record.
     * @return {@link Cursor} containing the insect result.
     */
    public Cursor queryInsectsById(int id) {
        //TODO: Implement the query
        Cursor cursor = null;
        SQLiteDatabase db = mBugsDbHelper.getWritableDatabase();
        try {
            cursor = db.query(InsectEntry.TABLE_NAME, null, "_id=?",
                    new String[]{String.valueOf(id)}, null, null, null);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursor;
    }
}
