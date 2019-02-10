package com.inhostudios.visionapitester;

import java.util.ArrayList;

public class FoodPrints {

    public static void main(String[] args) throws Exception{
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\thing.jpg";

        try{
            ImageInterpreter imageInterpreter = new ImageInterpreter(path);
            ArrayList<String> temp = imageInterpreter.getOutputs();
            for(int i = 0; i < temp.size(); i++){
                System.out.println("Item: " + temp.get(i));
            }
        } catch (Exception e){
            e.printStackTrace();
        }


    }

}
