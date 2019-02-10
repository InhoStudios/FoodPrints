package com.inhostudios.visionapitester.DataExtractionModel;

import com.google.gson.*;
import com.inhostudios.visionapitester.DataExtraction;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Recipe {
    private double calories;
    private double fat;
    private double protein;
    private double carbs;
    private double fibre;
    private String directions; // unused
    private int cooktime;
    private String urlToRecipe;
    private int servings;
    private String[] dietLabels;

    public Recipe(Object jsonObj){
        String jsonString = jsonObj.toString();
        JsonParser parser = new JsonParser();
        JsonObject jobj = parser.parse(jsonString).getAsJsonObject();
        JsonObject recipe = jobj.getAsJsonObject("recipe");
        // calories
        JsonPrimitive CALORIES = recipe.getAsJsonPrimitive("calories");

        JsonObject nutrients = recipe.getAsJsonObject("totalNutrients");
        // fat
        JsonPrimitive FAT = nutrients.getAsJsonObject("FAT").getAsJsonPrimitive("quantity");
        // protein
        JsonPrimitive PROTEIN = nutrients.getAsJsonObject("PROCNT").getAsJsonPrimitive("quantity");
        // carbs
        JsonPrimitive CARBS = nutrients.getAsJsonObject("CHOCDF").getAsJsonPrimitive("quantity");
        // fibre
        JsonPrimitive FIBRE = nutrients.getAsJsonObject("FIBTG").getAsJsonPrimitive("quantity");
        // cooktime
        JsonPrimitive COOKTIME = recipe.getAsJsonPrimitive("totalTime");
        // urlToRecipe
        JsonPrimitive URL = recipe.getAsJsonPrimitive("url");
        // servings
        JsonPrimitive SERVINGS = recipe.getAsJsonPrimitive("yield");
        // diet labels
        JsonArray DIETLABELS = recipe.getAsJsonArray("dietLabels");

        calories = CALORIES.getAsDouble();
        fat = FAT.getAsDouble();
        protein = PROTEIN.getAsDouble();
        carbs = CARBS.getAsDouble();
        fibre = FIBRE.getAsDouble();
        cooktime = COOKTIME.getAsInt();
        urlToRecipe = URL.getAsString();
        servings = SERVINGS.getAsInt();
        for(int i = 0; i < DIETLABELS.size(); i++){
            dietLabels[i] = DIETLABELS.get(i).getAsString();
        }

        System.out.println("Calories: " + calories + " Fat: " + fat + " URL: " + urlToRecipe);

    }

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

    // don't need this shit anymore vvvvvv
//    public static void setData(String queryStr, int minCal, int maxCal, int minTime, int maxTime){
//        // --- Format Query, these are possible attributes
//        RecipeQuery query = new RecipeQuery(queryStr);
//        query.setCalories(minCal, maxCal);
//        query.setTime(minTime, maxTime);
//        query.setDiet(Diet.LOW_CARB.toString());  // //one of “balanced”, “high-protein”, “high-fiber”, “low-fat”, “low-carb”, “low-sodium”
//        String url = query.toURL(); // output query to url string
//
//        // --- HTTP REQUEST
//        DataExtraction dataExtraction = new DataExtraction();
//        ArrayList<Object> list = dataExtraction.getEdamamRecipes(url);
//
//        String jsonString = list.get(0).toString();
//        System.out.println(jsonString);
//        JsonParser parser = new JsonParser();
//        JsonObject jobj = parser.parse(jsonString).getAsJsonObject();
//        JsonObject recipe = jobj.getAsJsonObject("recipe");
//        JsonPrimitive cals = recipe.getAsJsonPrimitive("calories");
//
//        System.out.println(cals.getAsInt());
//
//    }



    public static String getJsonObjectAsString(JsonArray jsonObject){
        return jsonObject.toString();
    }

    // TODO: take a list of json objects and return a list of Recipe Object
    public Recipe[] extractListOfRecipe(Object[] objects){
        return null; // stub
    }

    // get all the attributes


    public double getCalories() {
        return calories;
    }

    public double getFat() {
        return fat;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFibre() {
        return fibre;
    }

    public String getDirections() {
        return directions;
    }

    public int getCooktime() {
        return cooktime;
    }

    public String getUrlToRecipe() {
        return urlToRecipe;
    }

    public int getServings() {
        return servings;
    }

    public String[] getDietLabels(){
        return dietLabels;
    }
}
