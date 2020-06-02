package com.company;
import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

    JFrame frame;

    Window(String title, int width, int height, Main main){

        //Creates a frame for the window
        frame = new JFrame(title);

        //Sets the size for the frame
        frame.setSize(width, height);

        //Lets users close the program by pressing the red X
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Makes it so that the user can't resize the window
        frame.setResizable(false);

        //gives the frame a reference to Main class
        frame.add(main);

        //Sets the frame to be visible
        frame.setVisible(true);
    }

    public JFrame GetFrame(){
        return frame;
    }

}
