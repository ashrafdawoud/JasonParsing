package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /*كتابة الجاسون keys كمتغيرات في الكلاس مش شرط
    * ولكن هي طريقة مميزة في المشاريع الكبيرة منعا للاخطاء في حالة كتابة الكاي بشكل مباشر
    * خاصة عند تكرار استخدام الكاي اكثر من مرة */
    private final static String NAME_JSON_KEY = "name";
    private final static String MAINNAME_JSON_KEY = "mainName";
    private final static String ALSOKNOWNAS_JSON_KEY = "alsoKnownAs";
    private final static String PLACEOFORIGIN_JSON_KEY = "placeOfOrigin";
    private final static String DESCRIPTION_JSON_KEY = "description";
    private final static String IMAGE_JSON_KEY = "image";
    private final static String INGREDIENTS_JSON_KEY = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = null;
        if (json != null && !json.isEmpty()) {
            JSONObject sandwichJSON = new JSONObject(json);

            /*
            الافضل استخدام ميثود التي تبدا ب opt بدلا من الميثود التي تبدا ب get
            لانها تقوم بارجاع قيم فارغة وليست قيم ب null في حالة عدم وجود نتيجة
            JSONObject nameJSON = sandwichJSON.getJSONObject(NAME_JSON_KEY);
            */
            JSONObject nameJSON = sandwichJSON.optJSONObject(NAME_JSON_KEY);
            String mainName = nameJSON.optString(MAINNAME_JSON_KEY);
            List<String> alsoKnownAs = new ArrayList<>();
            JSONArray alsoKnownAsJSON = nameJSON.optJSONArray(ALSOKNOWNAS_JSON_KEY);
            for (int i=0; i<alsoKnownAsJSON.length(); i++){
                String alsoKnownAsName = alsoKnownAsJSON.optString(i);
                alsoKnownAs.add(alsoKnownAsName);
            }

            String placeOfOrigin = sandwichJSON.optString(PLACEOFORIGIN_JSON_KEY);

            String description = sandwichJSON.optString(DESCRIPTION_JSON_KEY);

            String image = sandwichJSON.optString(IMAGE_JSON_KEY);

            List<String> ingredients = new ArrayList<>();
            JSONArray ingredientsJSON = sandwichJSON.optJSONArray(INGREDIENTS_JSON_KEY);
            for (int i=0; i<ingredientsJSON.length(); i++){
                String ingredient = ingredientsJSON.optString(i);
                ingredients.add(ingredient);
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        }
        return sandwich;
    }
}
