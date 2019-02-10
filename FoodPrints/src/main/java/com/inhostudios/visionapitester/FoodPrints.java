package com.inhostudios.visionapitester;

import java.util.ArrayList;

public class FoodPrints {

    public static void main(String[] args){

        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\image.jpg";

        // webcam creator

        // end of webcam shit

        ImageInterpreter imageInterpreter = new ImageInterpreter(path);
        ArrayList<String> outputs = imageInterpreter.getOutputs();
        for(String output : outputs){
            System.out.println(output);
        }


    }

}
