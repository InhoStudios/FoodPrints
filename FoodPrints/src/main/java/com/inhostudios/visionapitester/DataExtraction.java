package com.inhostudios.visionapitester;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;


enum Diet {
    // TODO: not sure if this is the correct implementation
    BALANCED("balanced"),
    HIGH_PROTEIN("high-protein"),
    HIGH_FIBER("high-fiber"),
    LOW_FAT("low-fat"),
    LOW_CARB("low-carb"),
    LOW_SODIUM("low-sodium");

    private final String text;

    Diet(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

public class DataExtraction {
    private Gson gson = new Gson();
    private String recipeAppID = "ab5047e9";
    private String recipeAppKey = "391d21268595ea4650e3d0f9039af88f";
    private String foodAppID = "bb08a934";
    private String foodAppKey = "109788009c2c1ca6ff5ac36a4698aa0a";
    private String requirement = "";

    /*
     * possible requirements:
     * - parser?ingr=red%20apple    food search
     * - search?q=chicken           recipe search
     *
     *
     */


    public static void main(String[] args) {
        String jsonString = DataExtraction.sendRequest(null, null); // stub
        JsonObject object = DataExtraction.jsonStringToObject(jsonString);
    }

    private class DummyClass {
    }


    public static String sendRequest(String userID, String appKey) {
        String result = "";
        try {
            URL urlForGetRequest = new URL("https://api.edamam.com/search?q=chicken&app_id=ab5047e9&app_key=391d21268595ea4650e3d0f9039af88f&from=0&to=3&health=alcohol-free&calories=591-722");
            String readLine = null;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            int responseCode = conection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();

                // print result
                System.out.println("JSON String Result " + response.toString());

                result = response.toString();

            } else {
                System.out.println("GET NOT WORKED");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JsonObject jsonStringToObject(String jsonString) {
        try {
            JsonReader jsonReader = new JsonReader(new StringReader(jsonString));
            Gson gson = new Gson();
            return gson.fromJson(jsonReader, JsonObject.class);
        } catch (Error err) {
            System.out.println(err);
        }
        return null;
    }

    private class recipeQuery {
        private String q; // query text
        private String r; // ID of recipe
        private String appID = recipeAppID;
        private String appKey = recipeAppKey;
        private Integer from = 0;
        private Integer to = 3;
        private String diet = Diet.BALANCED.toString(); //one of “balanced”, “high-protein”, “high-fiber”, “low-fat”, “low-carb”, “low-sodium”
        private String range = "100-300";
        private String time;




        private String toURL() {
            String result = "https://api.edamam.com/search?q=chicken&app_id=ab5047e9&app_key=391d21268595ea4650e3d0f9039af88f&from=0&to=3&health=alcohol-free&calories=591-722";
            return "";
        }

        /* TIME
         * Time range for the total cooking and prep time for a recipe .
         * The format is time=RANGE where RANGE is replaced by the value in minutes.
         * RANGE is in one of MIN+, MIN-MAX or MAX, where MIN and MAX are non-negative integer numbers.
         * The + symbol needs to be properly encoded.
         * Examples: “time=1%2B” will return all recipes with available total time greater then 1 minute*/


        /* RANGE
         * The format is calories=RANGE where RANGE is replaced by the value in kcal.
         * RANGE is in one of MIN+, MIN-MAX or MAX, where MIN and MAX are non-negative
         * integer numbers. The + symbol needs to be properly encoded.
         * Examples: “calories=100-300” will return all recipes with which have
         * between 100 and 300 kcal per serving.
         * */

        /* EXCLUDING
         * Excluding recipes with certain ingredients.
         * The format is excluded=FOOD where FOOD is replaced by the name of the specific
         * food you don’t want to be present in the recipe results. More than one food can
         * be excluded at the same time. Example: excluded=vinegar&excluded=pretzel will
         * exclude any recipes which contain vinegar or pretzels in their ingredient list
         * */

    }



}



