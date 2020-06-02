package com.company;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseInput extends MouseAdapter {

    Handler handler;

    Camera cam;

    String[] textures = {"Assets/GroundTile.png", "Assets/GrassTile.png", "Assets/StoneTile.png"};
    int currentBlock = 0;

    MouseInput(Handler handler, Camera cam){
        this.handler = handler;
        this.cam = cam;
    }

    public void mousePressed(MouseEvent e){
        int x = e.getX();
        int y = e.getY();

        //System.out.println(x);
        //System.out.println(y);
        System.out.println(e);

        if (e.getButton() == 1){
            handler.DestroyBlock(x, y);
        }
        else if (e.getButton() == 3){
            ImageIcon textureTemp = new ImageIcon(textures[currentBlock]);
            handler.PlyerPlaceBlock(x, y, textureTemp);
        }

    }

    public void mouseWheelMoved(MouseWheelEvent e){

        int wheelRot = e.getWheelRotation();
        currentBlock += wheelRot;
        System.out.println(currentBlock);

        if (currentBlock >= textures.length){
            currentBlock = 0;
        }

        if (currentBlock < 0){
            currentBlock = textures.length - 1;
        }
    }
}
