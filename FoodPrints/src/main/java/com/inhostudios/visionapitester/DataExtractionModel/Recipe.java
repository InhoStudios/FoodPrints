package com.inhostudios.visionapitester.DataExtractionModel;

import com.google.api.client.json.JsonObjectParser;
import com.google.gson.JsonObject;
import io.grpc.internal.JsonParser;

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


    public JsonObject readFromJsonFile(String path) throws Exception{
//        File file = new File(path);
//        FileReader fr = new FileReader(file);
//        BufferedReader br = new BufferedReader(fr);
//
//        String jsonString = "";
//        String line = "";
//        while(!(line = br.readLine()).equals(null)){
//            jsonString = jsonString + line;
//        }

        JsonObject jobj = new JsonObject();
//        JSONParser jparser = new JSONParser();
//
//        InputStream is = ;

        return jobj;
    }

    // TODO: take a list of json objects and return a list of Recipe Object
    public Recipe[] extractListOfRecipe(Object[] objects){
        return null; // stub
    }

}
