package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Player extends GameObject {

    private int speed = 4;

    private int xVelocity;
    private int yVelocity;

    ImageIcon image = new ImageIcon("Assets/PlayerCharacter.png");
    Collider collider;

    boolean canFall = true;
    boolean canJump = true;
    boolean canWalkLeft = true;
    boolean CanWalkRight = true;
    boolean canAscend = true;

    boolean temp_canFall = true;
    boolean temp_canAscend = true;
    boolean temp_canWalkLeft = true;
    boolean temp_CanWalkRight = true;

    boolean moveRight;
    boolean moveLeft;

    //measures Ticks between jumps NOT SECONDS
    int jumpTimer = 12;
    int timer = 0;

    Player(int xPos, int yPos) {
        super(xPos, yPos);

        collider = new Collider(xPos, yPos, 24, 48);
    }

    public void Tick() {

    }


    public void Update(LinkedList<Tile> activeTiles) {

        timer++;

        if (activeTiles.size() == 0){
            canWalkLeft = true;
            CanWalkRight = true;
            canJump = false;
            canFall = true;
            canAscend = true;
        }

        temp_canFall = true;
        temp_canWalkLeft = true;
        temp_CanWalkRight = true;
        temp_canAscend = true;

        for (int i = 0; i < activeTiles.size(); i++){
            Tile temp = activeTiles.get(i);

            Collider tempCol = temp.GetCollider();

            HandlePhysics(tempCol);

            if (!canFall) temp_canFall = false;
            if (!canWalkLeft) temp_canWalkLeft = false;
            if (!CanWalkRight) temp_CanWalkRight = false;
            if (!canAscend) temp_canAscend = false;
        }

        canFall = temp_canFall;
        canWalkLeft = temp_canWalkLeft;
        CanWalkRight = temp_CanWalkRight;
        canAscend = temp_canAscend;

        Ascending();
        Gravity();
        MovementUpdate();
        yPos += yVelocity;
        xPos += xVelocity;
        collider.UpdatePosition(xPos, yPos);
    }

    public void Render(Graphics g, Camera c, JFrame f) {

        int x = c.ReturnWorldPositionX(xPos);
        int y = c.ReturnWorldPositionY(yPos);

        image.paintIcon(f, g, x, y);
    }

    public void MovementUpdate(){

        if (xVelocity < 0 && !canWalkLeft){
            xVelocity = 0;
        }

        if (xVelocity > 0 && !CanWalkRight){
            xVelocity = 0;
        }

        if (moveLeft && canWalkLeft){
            xVelocity = -speed;
            return;
        }

        if (moveRight && CanWalkRight){
            xVelocity = speed;
        }

        if (!moveLeft && !moveRight){
            xVelocity = 0;
        }
    }

    public void Jump(){
        if (!canJump || timer <= jumpTimer) return;
        yVelocity -= 24;
        canJump = false;
        timer = 0;
    }
    public void MoveLeft(){
        if (!canWalkLeft) {
            xVelocity = 0;
            moveLeft = false;
            return;
        }
        moveLeft = true;
        //xVelocity = -speed;
    }
    public void MoveRight(){
        if (!CanWalkRight) {
            xVelocity = 0;
            moveRight = false;
            return;
        }
        moveRight = true;
        //xVelocity = speed;
    }
    public void StopMoving(){
        xVelocity = 0;
    }
    public void StopMovingLeft(){
        moveLeft = false;
    }
    public void StopMovingRight(){
        moveRight = false;
    }
    public void Gravity(){

        if (yVelocity <= -4){
            yVelocity += 4;
        }
        else if (canFall) {
            yVelocity = 4;
            return;
        }
        else{
            yVelocity = 0;
        }

    }
    public void Ascending(){
        if (canAscend == false){
            yVelocity = 0;
            return;
        }
    }

    public void HandlePhysics(Collider col){

        //System.out.println("Collision returns: " + collider.LeftCollisionCheck(col));

        canFall = !collider.BottomCollisionCheck(col);

        if (!canFall){
            canJump = true;
        }

        canWalkLeft = !collider.LeftCollisionCheck(col);

        CanWalkRight = !collider.RightCollisionCheck(col);

        canAscend = !collider.TopCollisionCheck(col);


        //System.out.println("Can fall: " + canFall);
        //System.out.println("Can walk left: " + canWalkLeft);
        //System.out.println("Can walk right: " + CanWalkRight);

    }
}
