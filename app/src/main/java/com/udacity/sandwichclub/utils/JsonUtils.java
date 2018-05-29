package com.udacity.sandwichclub.utils;

import android.util.JsonReader;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        Log.d(TAG, json);
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject name = jsonObject.getJSONObject("name");

            String mainName = name.getString("mainName");

            List<String> alsoKnownAs = new ArrayList<String>();
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            if(alsoKnownAsArray.length() < 1){
                alsoKnownAs = null;
            }
            else {
                for (int i = 0; i < alsoKnownAsArray.length(); i++){
                    alsoKnownAs.add(alsoKnownAsArray.getString(i));
                }
            }

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");

            String description = jsonObject.getString("description");

            String image = jsonObject.getString("image");

            List<String> ingredients = new ArrayList<String>();
            JSONArray ingredientArray = jsonObject.getJSONArray("ingredients");
            for (int i = 0; i < alsoKnownAsArray.length(); i++){
                ingredients.add(ingredientArray.getString(i));
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
