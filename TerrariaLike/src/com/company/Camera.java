package com.company;

import com.sun.org.apache.regexp.internal.RE;

import java.awt.*;

public class Camera {

    Player player;

    public int xPos;
    public int yPos;

    Camera(int xPos, int yPos, Player player) {
        this.xPos = xPos;
        this.yPos = yPos;

        this.player = player;
    }

    public void Tick() {
        int NewxPos = -player.GetPosX() + 400 - 12;

        xPos = NewxPos;

        int NewyPos = -player.GetPosY() + 300;

        yPos = NewyPos;

        //yPos -= 10;

    }

    public int ReturnWorldPositionX(int currentPosition){
        int newPos = currentPosition + xPos;
        return newPos;
    }

    public int ReturnWorldPositionY(int currentPosition){
        int newPos = currentPosition + yPos;
        return newPos;
    }

    public int GetPosX(){
        return this.xPos;
    }
    public int GetPosY(){
        return this.yPos;
    }
}
