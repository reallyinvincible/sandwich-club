package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            //Fetching data from JSON
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
            for (int i = 0; i < ingredientArray.length(); i++){
                ingredients.add(ingredientArray.getString(i));
            }
            //Creating a sandwich object with obtained data
            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
