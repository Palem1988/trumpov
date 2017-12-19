package com.danklemme.trumpov;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by klemm023 on 10/26/2017.
 */

class TextAssetLoader {

    ArrayList<String> loadAssets(Context context, String folder) {
        AssetManager assetManager = context.getAssets();
        try {
            String[] assetFiles = assetManager.list(folder);
            ArrayList<String> assetText = new ArrayList<>();
            for (String file: assetFiles) {
                InputStream inStream = assetManager.open(folder + "/" + file);
                assetText.add(getInStreamText(inStream));
                inStream.close();
            }
            return assetText;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getInStreamText(InputStream stream) {
        if (stream != null) {
            InputStreamReader inputReader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            String line, line1 = "";
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    line1 += line;
                }
                inputReader.close();
                bufferedReader.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return line1;
        }
        return "";
    }

}
