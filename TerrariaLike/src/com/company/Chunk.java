package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Chunk extends GameObject{

    LinkedList<Tile> tiles = new LinkedList<Tile>();

    Handler handler;
    Camera cam;

    Chunk(int xPos, int yPos, Handler handler, Camera cam) {
        super(xPos, yPos);
        this.handler = handler;
        this.cam = cam;
    }

    public void AddTile(Tile newTile){
        tiles.add(newTile);
        //System.out.println("Added Tile");
    }

    public LinkedList<Tile> GetTiles(){
        return tiles;
    }

    public void Tick() {
        for (int i = 0; i < tiles.size(); i++){
            Tile temp = tiles.get(i);
            temp.Tick();
        }
    }

    public void ReturnTilesWithinDistance(Player player, int activationDisatance){
        int x1 = player.GetPosX() + 12; //X centre of player
        int y1 = player.GetPosY() + 24; //Y centre of player

        for (int i = 0; i < tiles.size(); i++){
            Tile t = tiles.get(i);
            int x2 = t.GetPosX() + 16;
            int y2 = t.GetPosY() + 16;

            double distance = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));

            if (distance <= activationDisatance){
                handler.AddActiveTiles(t);
            }
        }


    }

    public void Render(Graphics g, Camera c, JFrame f) {

        for (int i = 0; i < tiles.size(); i++){
            GameObject temp = tiles.get(i);
            temp.Render(g, c, f);
        }
    }

    public void CheckMouseClick(int xMouse, int yMouse){

        for (int i = 0; i < tiles.size(); i++){

            Tile temp = tiles.get(i);
            int xTile =  cam.ReturnWorldPositionX(temp.GetPosX());
            int yTile =  cam.ReturnWorldPositionY(temp.GetPosY());

            if (xMouse >= xTile && xMouse <= xTile + 32){
                if (yMouse >= yTile && yMouse <= yTile + 32){
                    tiles.remove(i);
                }
            }

        }
    }

    public boolean CheckIfSpaceIsTaken(int newTilePosX, int newTilePosY){

        for (int i = 0; i < tiles.size(); i++){
            Tile temp = tiles.get(i);
            if (newTilePosX == temp.GetPosX()){
                if (newTilePosY == temp.GetPosY()){
                    return false;
                }
            }
        }

        return true;
    }
}
