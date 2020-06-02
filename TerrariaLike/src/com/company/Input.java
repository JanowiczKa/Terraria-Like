package com.company;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Input extends KeyAdapter {

    public Player player;

    public Handler handler;

    Input(Player player, Handler handler){
        this.player = player;
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e){

        //Will return the pressed key into an ascii value
        int key = e.getKeyCode();

        //System.out.println(key);

        //65 = a and 68 == d
        if (key == 65) player.MoveLeft();
        if (key == 68) player.MoveRight();
        if (key == 32) player.Jump();
    }

    public void keyReleased(KeyEvent e){

        int key = e.getKeyCode();

        if (key == 65) player.StopMovingLeft();
        if (key == 68) player.StopMovingRight();
    }
}
