package com.company;

import javax.swing.*;
import java.awt.*;

public class Collider extends GameObject {

    int width;
    int height;

    Collider(int xPos, int yPos, int width, int height) {
        super(xPos, yPos);

        this.width = width;
        this.height = height;
    }

    public void UpdatePosition(int x, int y){
        xPos = x;
        yPos = y;
    }

    public int GetWidth(){
        return width;
    }
    public int GetHeight(){
        return height;
    }

    public boolean TopCollisionCheck(Collider col){

        //bottom of col colliding with top of this collider
        if (yPos >= col.GetPosY() && yPos <= col.GetPosY() + col.GetHeight()){

            if (xPos + width >= col.GetPosX() && xPos <= col.GetPosX() + col.GetWidth()) {

                return true;
            }
        }

        return false;
    }

    public boolean BottomCollisionCheck(Collider col){

        //bottom of this collider colliding with top of col
        if (col.GetPosY() <= yPos + height && col.GetPosY() + col.GetHeight() >= yPos + height){

            if (xPos + width >= col.GetPosX() && xPos <= col.GetPosX() + col.GetWidth()) {

                return true;
            }
        }

        return false;
    }

    public boolean LeftCollisionCheck(Collider col){

        //right side of col colliding with left side of this collider
        if (xPos >= col.GetPosX() && xPos <= col.GetPosX() + col.GetWidth()){

            //System.out.println("x collision");

            if (yPos < col.GetPosY() && yPos + height > col.GetPosY()){
                //System.out.println("y collision");
                return true;
            }
            else if (yPos <= col.GetPosY() + col.GetHeight() && yPos + height >= col.GetPosY() + col.GetHeight()){
                //System.out.println("y collision2");
                return true;
            }
        }
        return false;
    }

    public boolean RightCollisionCheck(Collider col){

        //left side of col colliding with right side of this collider
        if (xPos + width <= col.GetPosX() + col.GetWidth() && xPos + width >= col.GetPosX()){

            //System.out.println("x collision");

            if (yPos < col.GetPosY() && yPos + height > col.GetPosY()){
                //System.out.println("y collision");
                return true;
            }
            else if (yPos <= col.GetPosY() + col.GetHeight() && yPos + height >= col.GetPosY() + col.GetHeight()){
                //System.out.println("y collision2");
                return true;
            }
        }

        return false;
    }

    public void Tick() {

    }

    public void Render(Graphics g, Camera c, JFrame f) {

    }
}
