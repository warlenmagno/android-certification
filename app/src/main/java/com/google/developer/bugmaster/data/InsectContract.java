package com.google.developer.bugmaster.data;

import android.provider.BaseColumns;

/**
 * Created by lenwar on 29/07/2017.
 */

public class InsectContract {

    public static final class InsectEntry implements BaseColumns {

        public static final String TABLE_NAME = "insect";
        public static final String COLUMN_FRIENDLY_NAME = "friendlyName";
        public static final String COLUMN_SCIENTIFIC_NAME = "scientificName";
        public static final String COLUMN_CLASSIFICATION = "classification";
        public static final String COLUMN_IMAGE = "imageAsset";
        public static final String COLUMN_DANGER_LEVEL = "dangerLevel";
    }
}
