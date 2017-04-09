package com.zimincom.mafiaonline;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Zimincom on 2017. 4. 9..
 */

public  class Util {

    public static String getProperty(String key,Context context) throws IOException{


        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("app.propeties");
        properties.load(inputStream);

        return properties.getProperty(key);
    }
}
