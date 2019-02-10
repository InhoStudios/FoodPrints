package com.inhostudios.visionapitester.Camera;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Camera extends JComponent implements Runnable{

    private static final long serialVersionUID = 1L;

    private static CanvasFrame frame = new CanvasFrame("Webcam");
    private static boolean running = false;
    private static int frameWidth = 1280;
    private static int frameHeight = 720;
    private static OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
    private static BufferedImage bufImg;

    public Camera(){
        frame.setSize(frameWidth, frameHeight);

        ActionMap actionMap = frame.getRootPane().getActionMap();
        InputMap inputMap = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        for (Keys direction : Keys.values())
        {
            actionMap.put(direction.getText(), new KeyBinding(direction.getText()));
            inputMap.put(direction.getKeyStroke(), direction.getText());
        }

        frame.getRootPane().setActionMap(actionMap);
        frame.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

        // setup window listener for close action
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                stop();
            }
        });
    }

    public void run(){
        try {
            grabber.start();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        while(running){
            try{
                grabber.setImageWidth(frameWidth);
                grabber.setImageHeight(frameHeight);
                    final Frame cvimg = grabber.grab();
                    OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
                    opencv_core.IplImage img = converter.convert(cvimg);
                    if(cvimg != null){
                        frame.showImage(converter.convert(img));

                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        try {
            grabber.stop();
            grabber.release();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        frame.dispose();
    }
    public void start()
    {
        new Thread(this).start();
        running = true;
    }

    public void stop()
    {
        running = false;
    }

    private class KeyBinding extends AbstractAction {

        private static final long serialVersionUID = 1L;

        public KeyBinding(String text)
        {
            super(text);
            putValue(ACTION_COMMAND_KEY, text);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            String action = e.getActionCommand();
            if (action.equals(Keys.ESCAPE.toString()) || action.equals(Keys.CTRLC.toString())) stop();
            else System.out.println("Key Binding: " + action);
        }
    }
}

enum Keys
{
    ESCAPE("Escape", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0)),
    CTRLC("Control-C", KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK)),
    UP("Up", KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0)),
    DOWN("Down", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0)),
    LEFT("Left", KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0)),
    RIGHT("Right", KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));

    private String text;
    private KeyStroke keyStroke;

    Keys(String text, KeyStroke keyStroke)
    {
        this.text = text;
        this.keyStroke = keyStroke;
    }

    public String getText()
    {
        return text;
    }

    public KeyStroke getKeyStroke()
    {
        return keyStroke;
    }

    @Override
    public String toString()
    {
        return text;
    }
}