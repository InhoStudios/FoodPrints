package com.inhostudios.visionapitester;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FoodPrints {

    public static void main(String[] args){

        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\image.jpg";

        Webcam webcam = new Webcam.getDefault();
        webcam.open();
        BufferedImage image = webcam.getImage();
        try {
            ImageIO.write(image, "JPG", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageInterpreter imageInterpreter = new ImageInterpreter(path);
        ArrayList<String> outputs = imageInterpreter.getOutputs();
        for(String output : outputs){
            System.out.println(output);
        }


    }

}
