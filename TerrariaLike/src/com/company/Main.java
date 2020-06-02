package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

//Extends means it will inherit from the Canvas class
public class Main extends Canvas implements Runnable {

    private Thread thread;

    private boolean gameRunning;

    private Window window;
    private Handler Handler;
    private Camera mainCamera;
    private Player player;

    public int ChunksInWorld = 200;
    public int width = 800;
    public int height = 600;

    Main(){

        window = new Window("Terraria like", width, height, this);

        int worldWidth = (ChunksInWorld*32*10)/2;

        player = new Player(worldWidth, 0);

        mainCamera = new Camera(0, 0, player);

        Handler = new Handler(player, mainCamera);

        MouseInput mouseInput = new MouseInput(Handler, mainCamera);

        this.addKeyListener(new Input(player, Handler));
        this.addMouseListener(mouseInput);
        this.addMouseWheelListener(mouseInput);

        GenerateWorld();

        start();
    }

    public static void main(String[] args) {
        new Main();
    }

    //This will allocate a new thread object to the currently running game.
    public void start() {
        thread = new Thread(this);
        thread.start();
        gameRunning = true;
    }

    //This method will attempt to stop the game by turning off the loop and waits for the thread to die.
    public synchronized void stop(){
        try{
            thread.join();
            gameRunning = false;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void run(){

        int FPS = 0;
        int TargetTicksPerSecond = 30;
        // 1000000000 nano seconds = 1 second
        double TimeBetweenTicks = 1000000000 / TargetTicksPerSecond;
        double LastTickTime = System.nanoTime();
        double DeltaTime = 0;
        double lastFpsTime = 0;

        while (gameRunning){

            double Now = System.nanoTime();
            DeltaTime += (Now - LastTickTime) / TimeBetweenTicks;
            lastFpsTime += Now - LastTickTime;
            LastTickTime = Now;

            //System.out.println(lastFpsTime);

            while (DeltaTime >= 1){
                Tick();
                DeltaTime--;
            }

            Render();
            FPS++;

            if (lastFpsTime >= 1000000000)
            {
                System.out.println("FPS: "+ FPS);
                lastFpsTime = 0;
                FPS = 0;
            }

        }

        stop();

    }

    private void Tick(){

        mainCamera.Tick();
        Handler.Tick();

    }

    private void Render(){
        BufferStrategy bs = getBufferStrategy();

        if (bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLUE);
        g.fillRect(0, 0, width, height);

        JFrame f = window.GetFrame();

        Handler.Render(g, mainCamera, f);

        g.dispose();
        bs.show();
    }

    private void GenerateWorld(){

        int landLevel = 320;

        int ChunkWidth = 10;
        int worldDepth = 20;

        int MaxTileHeight = 3;
        int maxStoneHeight = 2;
        int minStoneHeight = 4;

        Random rand = new Random();

        int lastTileHeight = 0;

        //All the tiles are 32 px by 32 px

        for (int c = 0; c < ChunksInWorld; c++){

            int newX = c*(320);
            Chunk newChunk = new Chunk(newX,0, Handler, mainCamera);

            for (int i = 0; i < ChunkWidth; i++){

                int r = rand.nextInt(3) - 1; //returns an int between -1 and 1
                int newHeight = lastTileHeight + r;
                if (newHeight > MaxTileHeight || newHeight < -MaxTileHeight) newHeight = lastTileHeight;
                lastTileHeight = newHeight;

                r = rand.nextInt((minStoneHeight - maxStoneHeight) + 1) + maxStoneHeight;

                for (int j = 0; j < worldDepth - newHeight; j++){

                    ImageIcon textureTemp = new ImageIcon("Assets/GroundTile.png");

                    if (j == 0){
                        textureTemp = new ImageIcon("Assets/GrassTile.png");
                    }

                    if (j >= r){
                        textureTemp = new ImageIcon("Assets/StoneTile.png");
                    }

                    Tile newTile = new Tile((i*32)+(newX), ((j + newHeight)*32) + landLevel, textureTemp);

                    newChunk.AddTile(newTile);
                }


            }

            Handler.AddObject(newChunk);

        }

    }
}