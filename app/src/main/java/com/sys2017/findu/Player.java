package com.sys2017.findu;

/**
 * Created by N552VW on 2017-07-18.
 */

public class Player {

    boolean canMove = false;
    boolean canJump = false;    boolean isjump = false;
    boolean canAction = false;

    int player_x;
    int player_y;

    int joy_cx;
    int joy_cy;

    int touch_x;
    double touch_y;

    int width;
    int height;

    int ch_W;
    int ch_Y;

    int dx = 5;
    double dy = 5.0;

    double playerRadian;

    int speed = 2;

    public Player(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getCh_W() {
        return ch_W;
    }

    public void setCh_W(int ch_W) {
        this.ch_W = ch_W;
        player_x = width/2 - ch_W;
    }

    public int getCh_Y() {
        return ch_Y;
    }

    public void setCh_Y(int ch_Y) {
        this.ch_Y = ch_Y;
        player_y = height-(height/4) - ch_Y;
    }

    void move(){
        //이동!
        if ( canMove ){
            player_x += dx;
            if ( touch_x > joy_cx ){
                dx = 5;
            }else if ( touch_x < joy_cx ){
                dx = -5;
            }
        }
        //점프!
        if ( canJump ){
            dy = dy - 0.5;
            player_y -= dy;
            if ( player_y == height-(height/4) - ch_Y ){
                canJump = false;
                dy  = 9.0;
            }
        }

    }
}
