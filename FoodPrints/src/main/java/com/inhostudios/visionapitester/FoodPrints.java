package com.inhostudios.visionapitester;

import java.awt.*;
import java.util.ArrayList;

public class FoodPrints {

    public static void main(String[] args) throws Exception{
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\apple.jpg";

        ImageInterpreter imageInterpreter = new ImageInterpreter(path);
        ArrayList<String> outputs = imageInterpreter.getOutputs();
        for(String output : outputs){
            System.out.println(output);
        }


    }

}
