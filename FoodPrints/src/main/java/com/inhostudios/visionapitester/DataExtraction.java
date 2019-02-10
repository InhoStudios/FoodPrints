package com.inhostudios.visionapitester;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.inhostudios.visionapitester.DataExtractionModel.Diet;
import com.inhostudios.visionapitester.DataExtractionModel.RecipeQuery;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


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
        RecipeQuery query = new RecipeQuery("chocolate%20cheesecake");
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



