package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Handler {

    LinkedList<GameObject> objects = new LinkedList<GameObject>();

    LinkedList<Chunk> activeChunks = new LinkedList<Chunk>();

    LinkedList<Tile> activeTiles = new LinkedList<Tile>();

    LinkedList<Tile> PlayerTiles = new LinkedList<Tile>();

    public Player player;

    public Camera cam;

    Handler(Player player, Camera cam){
        this.player = player;
        this.cam = cam;
    }

    public void Tick(){

        int ActivateDistance = 64;
        activeTiles.clear();
        activeChunks.clear();

        for (int i = 0; i < objects.size(); i++){

            GameObject temp = objects.get(i);

            if (temp instanceof Chunk){

                int dist = ChunkDistance((Chunk)temp);
                //System.out.println("Chunk number " + i + " has distance: " +  dist);

                if (dist >= -800 && dist <= 800){

                    activeChunks.add((Chunk)temp);
                    temp.Tick();
                    ((Chunk) temp).ReturnTilesWithinDistance(player, ActivateDistance);
                }

            }
            else {
                temp.Tick();
            }

        }

        //System.out.println(PlayerTiles.size());

        //System.out.println("Chunks: " + objects.size());
        //System.out.println("Active Chunks: " + activeChunks.size());
        //System.out.println("Active Tiles: " + activeTiles.size());

        //enables collisions on tiles that are close to the player
        player.Update(activeTiles);
    }

    public void Render(Graphics g, Camera c, JFrame f){

        //double curr = System.nanoTime();

        for (int i = 0; i < activeChunks.size(); i++){
            activeChunks.get(i).Render(g, c, f);
        }

        //System.out.println("Time to render world: " + (System.nanoTime() - curr));


        player.Render(g, c, f);
    }

    public void AddObject(GameObject newObject){

        objects.add(newObject);

    }

    public void AddActiveTiles(Tile newActiveTile){
        activeTiles.add(newActiveTile);
    }

    public int ChunkDistance(Chunk currentChunk){
        int x1 = player.GetPosX();
        int x2 = currentChunk.GetPosX();

        int distance = x2 - x1;

        return  distance;
    }

    public void DestroyBlock(int xMouse, int yMouse){

        for (int i = 0; i < activeChunks.size(); i++){

            Chunk temp = activeChunks.get(i);

            temp.CheckMouseClick(xMouse, yMouse);
        }
    }

    public void PlyerPlaceBlock(int xMouse, int yMouse, ImageIcon texture){

        int newMouseX = xMouse - cam.GetPosX();
        int newMouseY = yMouse - cam.GetPosY();

        if (newMouseY % 32 != 0){
            int remained = newMouseY % 32;
            newMouseY -= remained;
        }

        if (newMouseX % 32 != 0){
            int remained = newMouseX % 32;
            newMouseX -= remained;
        }

        Chunk closestChunk = activeChunks.get(0);
        int shortestDist = closestChunk.GetPosX() - newMouseX;

        if (shortestDist < 0) shortestDist *= -1;

        for (int i = 0; i < activeChunks.size(); i++){

            Chunk temp = activeChunks.get(i);
            int x1 = temp.GetPosX();
            int dist = x1 - newMouseX;

            if (dist < 0) dist *= -1;

            //System.out.println("Distance for iteration number " + i + " is:" + dist);
            //System.out.println(shortestDist);

            if (dist < shortestDist) {
                shortestDist = dist;
                closestChunk = activeChunks.get(i);
            }
        }

        if (closestChunk.CheckIfSpaceIsTaken(newMouseX, newMouseY)){
            Tile newTile = new Tile(newMouseX, newMouseY, texture);
            closestChunk.AddTile(newTile);
        }


        //System.out.println("Shortest distance: " + shortestDist);
        //System.out.println("Mouse X: " + newMouseX + " | Mouse Y: " + newMouseY);
        //System.out.println("Camera X: " + cam.GetPosX() + " | Camera Y: " + cam.GetPosY());
        //System.out.println("Tile X: " + newTile.GetPosX() + " | Tile Y: " + newTile.GetPosY());
    }

}
