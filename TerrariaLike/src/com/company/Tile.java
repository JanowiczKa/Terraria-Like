package com.company;

import javax.swing.*;
import java.awt.*;

public class Tile extends GameObject {

    public ImageIcon tileTexture;

    public Collider collider;

    Tile(int xPos, int yPos, ImageIcon texture) {
        super(xPos, yPos);
        this.tileTexture = texture;
        collider = new Collider(xPos, yPos, 32, 32);
    }

    public void Tick() {

    }

    public void Render(Graphics g, Camera c,  JFrame f) {

        int x = c.ReturnWorldPositionX(xPos);
        int y = c.ReturnWorldPositionY(yPos);

        tileTexture.paintIcon(f, g, x, y);
    }

    public Collider GetCollider(){
        return collider;
    }
}
