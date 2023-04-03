package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Snake {
    private int dx, dy;
    private int x,y;
    private int N_size, n;
    boolean head = false;
    public Snake(int N, int SIZE){
        this.x = (int)(n/2);
        this.y = (int)(n/2);
        this.n = N;
        this.N_size = SIZE;
    }
    public void setHead(boolean head){
        this.head = head;
    }

    public void draw(ShapeRenderer sr) {
        if (head){
            sr.setColor(0.1f, 0.25f, 3, 1);
        }else{
            sr.setColor(0, 0.25f, 0, 1);
        }
        sr.begin(ShapeRenderer.ShapeType.Filled);
        float x_size = x * N_size;
        float y_size = y * N_size;
//        System.out.println(x_size + " " + y_size);
        sr.rect(x_size, y_size, N_size, N_size);
        sr.end();
        sr.setColor(0, 2, 0, 1);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.rect(x_size, y_size, N_size, N_size);
        sr.end();

    }
    public int getX() {
        return x;
    }
    public void setPosition(){
        this.x += dx;
        this.y += dy;
    }
    public void setPosition(int nx, int ny){
        this.x = nx;
        this.y = ny;
    }

        public int getY(){
            return y;
        }

    public int getDx(){
        return dx;
    }

    public int getDy(){
        return dy;
    }
}
