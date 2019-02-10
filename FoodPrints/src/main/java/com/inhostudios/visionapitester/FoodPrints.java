package com.inhostudios.visionapitester;

import com.inhostudios.visionapitester.Camera.Camera;

import java.util.ArrayList;

public class FoodPrints {

    public static void main(String[] args){

        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\apple.jpg";

        // camera
        Camera cam = new Camera();
        cam.start();
        // no more camera

        ImageInterpreter imageInterpreter = new ImageInterpreter(path);
        ArrayList<String> outputs = imageInterpreter.getOutputs();
        for(String output : outputs){
            System.out.println(output);
        }


    }

}
