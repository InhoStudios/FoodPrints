package com.inhostudios.visionapitester;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class DataExtraction {
    private Gson gson = new Gson();


    public static void main(String[] args) {
        String jsonString = DataExtraction.sendRequest();

        JsonObject object = DataExtraction.jsonStringParser(jsonString);
        System.out.println("j");
    }


    // TODO: search by natural language processing (parser)

    // TODO: find a recipe that fits all the possible food

    private class DummyClass{}


    public static String sendRequest() {
        String result = "";
        try {
//            URL urlForGetRequest = new URL("https://jsonplaceholder.typicode.com/posts/1");
            String userID = "bb08a934";
            String appKey = "109788009c2c1ca6ff5ac36a4698aa0a";
            String requirement = "parser?ingr=red%20apple";
            URL urlForGetRequest = new URL("https://api.edamam.com/api/food-database/" + requirement + "&app_id=" + userID + "&app_key=" + appKey);
            String readLine = null;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
//            conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
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

    public static JsonObject jsonStringParser(String jsonString) {
        try {
            JsonReader jsonReader = new JsonReader(new StringReader(jsonString));
            Gson gson = new Gson();
            return gson.fromJson(jsonReader, JsonObject.class);
        } catch (Error err) {
            System.out.println(err);
        }
        return null;
    }
}



