package com.inhostudios.visionapitester;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataExtraction {
    public static void main(String[] args) {
        String jsonString = DataExtraction.sendRequest();
//        DataExtraction.parseFromJSONResponse(jsonString);
    }


    public static String sendRequest() {
        String result = "";
        try {
//            URL urlForGetRequest = new URL("https://jsonplaceholder.typicode.com/posts/1");
            URL urlForGetRequest = new URL("https://api.edamam.com/api/food-database/parser?ingr=red%20apple&app_id=bb08a934&app_key=109788009c2c1ca6ff5ac36a4698aa0a");
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
                //GetAndPost.POSTRequest(response.toString());
            } else {
                System.out.println("GET NOT WORKED");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
