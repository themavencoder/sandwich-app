package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static JSONObject jsonObject;
    private static JSONArray jsonArray;




    public JsonUtils(Sandwich sandwich, JSONObject jsonObject, JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        this.jsonObject = jsonObject;

    }


    public static Sandwich parseSandwichJson(String json)  {
        String mainName = null;
        List<String> alsoKnownAs  = new ArrayList<>();
        String placeOfOrigin = null;
        String description = null;
        List<String> ingredients = new ArrayList<>();
        String image = null;

        try {
            jsonObject = new JSONObject(json);
            JSONObject ObjectName = jsonObject.getJSONObject("name");
            mainName = ObjectName.optString("mainName");
            description = jsonObject.optString("description");
            placeOfOrigin = jsonObject.optString("placeOfOrigin");
            image = jsonObject.optString("image");
            alsoKnownAs = jsonLists(ObjectName.getJSONArray("alsoKnownAs"));
            ingredients = jsonLists(jsonObject.getJSONArray("ingredients"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description,image,ingredients);
    }

    private static List<String> jsonLists(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();

        if(jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));


            }

        } else {
            list.add("");
        }
        return  list;

    }
}
