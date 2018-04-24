package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        String mainName="";
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin="";
        String description="";
        String image="";
        List<String> ingredients = new ArrayList<>();

        if (json != null){
            try {
                JSONObject sandwich = new JSONObject(json);
                JSONObject name = sandwich.getJSONObject("name");

                mainName = name.getString("mainName");

                JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
                if (alsoKnownAsArray != null){
                    for (int i=0;i<alsoKnownAsArray.length();i++){
                        alsoKnownAs.add(alsoKnownAsArray.getString(i));
                    }
                }

                placeOfOrigin = sandwich.getString("placeOfOrigin");

                description = sandwich.getString("description");

                image = sandwich.getString("image");

                JSONArray ingredientsArray = sandwich.getJSONArray("ingredients");
                if (ingredientsArray != null){
                    for (int i=0;i<ingredientsArray.length();i++){
                        ingredients.add(ingredientsArray.getString(i));
                    }
                }
            } catch (final JSONException e){

            }
        }
        Sandwich sandwich = new Sandwich();
        sandwich.setMainName(mainName);
        sandwich.setAlsoKnownAs(alsoKnownAs);
        sandwich.setDescription(description);
        sandwich.setPlaceOfOrigin(placeOfOrigin);
        sandwich.setImage(image);
        sandwich.setIngredients(ingredients);

        return sandwich;
    }
}
