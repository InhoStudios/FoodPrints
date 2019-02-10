package com.inhostudios.visionapitester;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.inhostudios.visionapitester.Camera.Camera;
import com.inhostudios.visionapitester.DataExtractionModel.Recipe;
import com.inhostudios.visionapitester.GUI.Display;

import java.util.ArrayList;

public class FoodPrints {

    private static int width = 1280, height = 720;

    public static void main(String[] args){
        Display display = new Display("FoodPrints", width, height);
        // camera
//        Camera cam = new Camera();
//        cam.start();
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
