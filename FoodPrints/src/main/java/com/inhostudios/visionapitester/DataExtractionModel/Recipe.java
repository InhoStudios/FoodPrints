package com.inhostudios.visionapitester.DataExtractionModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inhostudios.visionapitester.DataExtraction;

import java.io.*;
import java.util.ArrayList;

public class Recipe {
    private static String calories;
    private String fat;
    private String protein;
    private String carbs;
    private String fibre;
    private String directions;
    private String cooktime;
    private String urlToRecipe;
    private int servings;

    // needs fixes but keep it for now
    public static JsonArray readFromJsonFile(String path){
        JsonArray jobj = new JsonArray();

        try{
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(path));
            jobj = jsonElement.getAsJsonArray();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

        return jobj;
    }

    public static void setData(String queryStr, int minCal, int maxCal, int minTime, int maxTime){
        // --- Format Query, these are possible attributes
        RecipeQuery query = new RecipeQuery(queryStr);
        query.setCalories(minCal, maxCal);
        query.setTime(minTime, maxTime);
        query.setDiet(Diet.LOW_CARB.toString());  // //one of “balanced”, “high-protein”, “high-fiber”, “low-fat”, “low-carb”, “low-sodium”
        String url = query.toURL(); // output query to url string

        // --- HTTP REQUEST
        DataExtraction dataExtraction = new DataExtraction();
        ArrayList<Object> list = dataExtraction.getEdamamRecipes(url);

        String jsonString = "{\"recdata\":" + list.toString() + "};";
        System.out.println("\nJson Got! \n");
        JsonElement jElement = new JsonParser().parse(jsonString);
        JsonObject jobj = jElement.getAsJsonObject();

        // assigning values from the json object
        calories = jobj.getAsJsonObject("recipe").get("calories").toString();
        System.out.println(calories);
    }



    public static String getJsonObjectAsString(JsonArray jsonObject){
        return jsonObject.toString();
    }

    // TODO: take a list of json objects and return a list of Recipe Object
    public Recipe[] extractListOfRecipe(Object[] objects){
        return null; // stub
    }

}
