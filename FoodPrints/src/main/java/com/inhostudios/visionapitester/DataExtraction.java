package com.inhostudios.visionapitester;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


enum Diet {
    BALANCED("balanced"),         // TESTED
    HIGH_PROTEIN("high-protein"), // TESTED
    LOW_FAT("low-fat"),           // TESTED
    LOW_CARB("low-carb"),         // TESTED

    // NOT WORKING
    HIGH_FIBER("high-fiber"),
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

class RecipeQuery {
    private String recipeAppID = "&app_id=ab5047e9";
    private String recipeAppKey = "&app_key=391d21268595ea4650e3d0f9039af88f";

    private String q; // query text
    private String appID = recipeAppID;     // TESTED
    private String appKey = recipeAppKey;   // TESTED
    private String from = "&from=0";        // TESTED
    private String to = "&to=3";            // TESTED
    private String diet = "";               // TESTED
    private String calories = "";           // TESTED
    private String time = "";               // TESTED
    private String exluded = "";            // TESTED

    public RecipeQuery(String oneKeyWord){
        this.setQ(oneKeyWord);
    }


    public void setQ(String q) {
        this.q = "q=" + q;
    }


    public void setDiet(String diet) {
        this.diet = "&diet=" + diet;
    }

    public void setCalories(int minCal, int maxCal) {
        this.calories = "&calories=" + minCal + "-" + maxCal;
    }

    public void setTime(int min) {
        this.time = "&time=" + min + "%2B";
    }

    public void setTime(int min, int max) {
        this.time = "&time=" + min + "-" + max;
    }

    public void setExluded(String excludeFood) {
        this.exluded = "&excluded=" + excludeFood;
    }

    public String toURL() {
        String testing = "https://api.edamam.com/search?q=chicken&app_id=ab5047e9" +
                "&app_key=391d21268595ea4650e3d0f9039af88f&from=0&to=3&health=alcohol-free&calories=591-722";


        String result = "https://api.edamam.com/search?" + q + appID +
                appKey + from + to + "&health=alcohol-free" + calories + time + exluded + diet;
        return result;
    }

}

public class DataExtraction {
    private Gson gson = new Gson();
    private String recipeAppID = "ab5047e9";
    private String recipeAppKey = "391d21268595ea4650e3d0f9039af88f";
    private String foodAppID = "bb08a934";
    private String foodAppKey = "109788009c2c1ca6ff5ac36a4698aa0a";
    private String requirement = "";

    // EFFECTS; send request Edamam with a URL, return Json String from server
    public String sendRequest(String url) {
        String result = "";
        try {
            URL urlForGetRequest = new URL(url);
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

                System.out.println("JSON String Result " + response.toString()); // print result
                result = response.toString();                                    // return the json string
            } else {
                System.out.println("GET NOT WORKED, YOUR URL IS NOT FORMATTED CORRECTLY");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // EFFECTS: parse string to json object
    public JsonObject jsonStringParser(String jsonString) {
        try {
            JsonReader jsonReader = new JsonReader(new StringReader(jsonString));
            Gson gson = new Gson();
            return gson.fromJson(jsonReader, JsonObject.class);
        } catch (Error err) {
            System.out.println(err);
        }
        return null;
    }

    // EFFECTS: return an array of object (recipes) from Edamam Search Result
    public ArrayList<Object> extractHits(JsonObject object){
        JsonArray hits = object.getAsJsonArray("hits");
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            for (int i=0, l=hits.size(); i<l; i++){
                list.add(hits.get(i));
            }
        } catch (Exception e) {
            System.out.println("extractHits(): ERROR");
        }

        return list;
    }


    // EFFECTS: given an URL, return an array of object (recipes) from Edamam
    public ArrayList<Object> getEdamamRecipes(String url) {
        String jsonString = sendRequest(url); // stub
        JsonObject result = jsonStringParser(jsonString);
        return extractHits(result);
    }

//    public static ArrayList<Object>

    public static void main(String[] args) {

        // --- Format Query, these are possible attributes
        RecipeQuery query = new RecipeQuery("pork");
        query.setCalories(100, 700);
        query.setTime(10, 20);
        query.setDiet(Diet.LOW_CARB.toString());  // //one of “balanced”, “high-protein”, “high-fiber”, “low-fat”, “low-carb”, “low-sodium”
        String url = query.toURL(); // output query to url string


        // --- HTTP REQUEST
        DataExtraction dataExtraction = new DataExtraction();
        ArrayList<Object> list = dataExtraction.getEdamamRecipes(url);

        System.out.println(list.toString());
    }


}



