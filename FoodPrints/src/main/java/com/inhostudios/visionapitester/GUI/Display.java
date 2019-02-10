package com.inhostudios.visionapitester.GUI;

import com.inhostudios.visionapitester.Camera.Camera;
import com.inhostudios.visionapitester.DataExtractionModel.RecipeQuery;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.List;

public class Display {
    private JPanel panel1;

    private List accessList;
    private JEditorPane webpageDisplay;
    private JButton webpageButton;
    private JButton recipeButton;
    private JButton camButton;
    private JTextField searchTerms;
    private Camera camera;

    public Display(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setContentPane(this.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.pack();

        // multiple select for list
        accessList.setMultipleMode(true);

        // create a camera
        camera = new Camera();
        camera.start();

        webpageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    webpageDisplay.setContentType("text/html");
                    webpageDisplay.setPage("http://inhostudios.tech");
                } catch(IOException exp){
                    webpageDisplay.setContentType("text/html");
                    webpageDisplay.setText("<html>Could not load</html>");
                    System.out.println("Could not load");
                }
            }
        });
        camButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cameraList();
            }

            public void cameraList(){
                accessList.removeAll();
                ArrayList<String> potentials = camera.getOutputs();
                for(String hit : potentials){
                    accessList.add(hit);
                }
            }
        });
        recipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        accessList.addComponentListener(new ComponentAdapter() {
        });
        accessList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int indices[] = accessList.getSelectedIndexes();
                String srcterms = "";
                for(int index : indices){
                    srcterms = srcterms + ", " + accessList.getItem(index);
                }
                updateSelection(srcterms);
            }

            public void updateSelection(String str){
                searchTerms.setText(str);
            }
        });
    }
}
