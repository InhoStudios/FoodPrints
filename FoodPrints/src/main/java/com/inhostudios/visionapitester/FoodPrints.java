package com.inhostudios.visionapitester;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.inhostudios.visionapitester.Camera.Camera;
import com.inhostudios.visionapitester.DataExtractionModel.Recipe;

import java.util.ArrayList;

public class FoodPrints {
    public static void main(String[] args){
//        String path = getDir() +"recipes.json";
//        JsonArray jobj = Recipe.readFromJsonFile(path);
//        print(jobj.toString());
//        print(Recipe.getJsonObjectAsString(jobj));
        // camera
        Camera cam = new Camera();
        cam.start();
        // no more camera
    }

    //quick utils
    public static void print(String string){
        System.out.println(string);
    }

    public static String getDir(){
        return System.getProperty("user.dir") + "\\src\\main\\resources\\";
    }

}
