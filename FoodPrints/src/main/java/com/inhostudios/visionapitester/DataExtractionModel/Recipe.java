package com.inhostudios.visionapitester.DataExtractionModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class Recipe {
    private String calories;
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

    

    public static String getJsonObjectAsString(JsonArray jsonObject){
        return jsonObject.toString();
    }

    // TODO: take a list of json objects and return a list of Recipe Object
    public Recipe[] extractListOfRecipe(Object[] objects){
        return null; // stub
    }

}
