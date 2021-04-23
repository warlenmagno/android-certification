package com.google.developer.bugmaster.Utils;

import android.content.Context;

import com.google.developer.bugmaster.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lenwar on 29/07/2017.
 */

public class FileUtils {

    public static String loadJSONFromAsset(Context context) throws IOException{
        InputStream is = context.getResources().openRawResource(R.raw.insects);
        return toString(is);
    }

    public static String toString(InputStream is) throws IOException{
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }

}
