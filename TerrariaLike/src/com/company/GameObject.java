package com.company;

import javax.swing.*;
import java.awt.*;

public abstract class GameObject {

    public int xPos;
    public int yPos;

    GameObject(int xPos, int yPos){

        this.xPos = xPos;
        this.yPos = yPos;

    }

    public abstract void Tick();

    public abstract void Render(Graphics g, Camera c, JFrame f);

    public void SetPosition(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }
    public void SetPosX(int xPos){
        this.xPos = xPos;
    }
    public void SetPosY(int yPos){
        this.yPos = yPos;
    }
    public int GetPosX(){
        return this.xPos;
    }
    public int GetPosY(){
        return this.yPos;
    }

}
