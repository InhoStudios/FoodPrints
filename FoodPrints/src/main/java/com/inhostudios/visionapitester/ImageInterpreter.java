package com.inhostudios.visionapitester;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImageInterpreter {

    private static String output;
    private static ArrayList<String> outputs = new ArrayList<String>();
    private static String fileName;

    public ImageInterpreter(String fileName){
        this.fileName = fileName;

        try{
            processImage(fileName);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void processImage(String fileName) throws Exception{
        // try image recognition
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

            // Reads the image file into memory
            Path path = Paths.get(fileName);
            byte[] data = Files.readAllBytes(path);
            ByteString imgBytes = ByteString.copyFrom(data);

            // Builds the image annotation request
            List<AnnotateImageRequest> requests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feat)
                    .setImage(img)
                    .build();
            requests.add(request);

            // Performs label detection on the image file
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.printf("Error: %s\n", res.getError().getMessage());
                    return;
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    annotation.getAllFields().forEach((k, v) ->
                            System.out.printf("%s : %s\n", k, v.toString()));
                    String output = annotation.getDescription();
                    if(!output.toLowerCase().contains("food")) {
                        outputs.add(annotation.getDescription());
                    }
                }
            }
        }
    }

    public ArrayList<String> getOutputs(String fileName){
        try {
            processImage(fileName);
        } catch (Exception e){
            e.printStackTrace();
        }
        return outputs;
    }

    public ArrayList<String> getOutputs(){
        return outputs;
    }

}
