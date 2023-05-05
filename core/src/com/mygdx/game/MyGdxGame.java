package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
    public static final int SIZE = 800;
    private static final int N = 16;
    private static final int SIZE_N = SIZE / N;
    private int speed;
    private ArrayDeque<Snake> belochka = new ArrayDeque<Snake>();
    private Snake snake;
    private Apple apple;
    SpriteBatch batch;
    ShapeRenderer sr;
    Texture img;
    private long timegame;

    @Override
    public void create() {
        speed = 1;
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        apple = new Apple(N, SIZE_N);
        snake = new Snake(N, SIZE_N);
        snake.setHead(true);
        belochka.addFirst(snake);
        timegame = TimeUtils.millis();
    }

    public void update(ArrayDeque<Snake> belochk, Snake zmeya) {
        ArrayDeque<Snake> belochka_copy = new ArrayDeque<>(belochk);
        belochka_copy.addFirst(zmeya);
        List<Snake> list = new ArrayList<>(belochka_copy);
        int i = 0;
        float x = zmeya.getX();
        float y = zmeya.getY();
        int dx = zmeya.getDx();
        int dy = zmeya.getDy();
        int x_N = 0;
        int y_N = 0;
        if (y <= 0) {
            y_N = 1;
        }
        if (x <= 0){
            x_N = 1;
        }
        if (y >= N-1){
            y_N = -1;
        }
        if (x >= N-1){
            x_N = -1;
        }

        float x_new, y_new, dx_new, dy_new;
        for (Snake sn : belochk) {
            Snake head = list.get(i);
            x_new = sn.getX();
            y_new = sn.getY();
            dx_new = sn.getDx();
            dy_new = sn.getDy();
            sn.setPosition(x, y);
            sn.setDx(dx);
            sn.setDy(dy);
            x = x_new;
            y = y_new;
            dx = (int)dx_new;
            dy = (int)dy_new;
            i++;
        }
        pole(x_N, y_N);
    }

    private void pole(int x_n, int y_n) {
        for (Snake sn: belochka){
            sn.setPosition(sn.getX()+x_n, sn.getY()+y_n);
        }

       apple.setPosition(x_n, y_n);
    }

    public void cordinat(){
        float zmeyaX = belochka.getFirst().getX();
        float zmeyaY = belochka.getFirst().getY();
        for (Snake sn: belochka) {
            float xa = apple.getX();
            float ya = apple.getY();
            float a = sn.getX();
            float b = sn.getY();
            if (zmeyaX!= a && zmeyaY!= b){
                if (xa == a && ya == b){
                    apple.setPosition(1 , 0);

                }
            }
        }
    }

    @Override
    public void render() {
        cordinat();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.ORANGE);
        sr.rect(0, 0, SIZE, SIZE);
        sr.end();
        for (Snake sn : belochka) {
            sn.draw(sr);
        }

//       // if(TimeUtils.millis() - timegame > 2000/speed){
//            Snake zmeya = belochka.getFirst();
//            zmeya.setPosition(zmeya.getX()+zmeya.getDx(), zmeya.getY()+zmeya.getDy());
//            update(belochka, zmeya);
//            timegame = TimeUtils.millis();
//        }

        apple.draw(sr);
        Snake head = belochka.getFirst();
        float x = head.getX();
        float y = head.getY();
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            Snake zmeya = new Snake(N, SIZE_N);
            zmeya.setHead(false);
            zmeya.setPosition(x,y);
            zmeya.pressW();
            update (belochka, zmeya);
            timegame = TimeUtils.millis();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            Snake zmeya = new Snake(N, SIZE_N);
            zmeya.setHead(false);
            zmeya.setPosition(x,y);
            zmeya.pressS();
            update (belochka, zmeya);
            timegame = TimeUtils.millis();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            Snake zmeya = new Snake(N, SIZE_N);
            zmeya.setHead(false);
            zmeya.setPosition(x,y);
            zmeya.pressD();
            update (belochka, zmeya);
            timegame = TimeUtils.millis();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            Snake zmeya = new Snake(N, SIZE_N);
            zmeya.setHead(false);
            zmeya.setPosition(x,y);
            zmeya.pressA();
            update (belochka, zmeya);
            timegame = TimeUtils.millis();
        }
        head = belochka.getFirst();
        x = head.getX();
        y = head.getY();
        float xa = apple.getX();
        float ya = apple.getY();
        if (config(x, y, xa, ya)) {
            apple.setPosition();
            speed++;
            Snake last = belochka.getLast();
            float last_x = last.getX() - last.getDx();
            float last_y = last.getY() - last.getDy();
            Snake zmeya = new Snake(N, SIZE_N);
            zmeya.setHead(false);
            zmeya.setPosition(last_x, last_y);
            zmeya.setDx(last.getDx());
            zmeya.setDy(last.getDy());
            belochka.addLast(zmeya);
        }
    }

    private boolean config(float x, float y, float xa, float ya) {
        return (int) x == xa && (int) y == ya;
        // возвращение true или false.
        // True вернётся в случае если наши координаты совпадают.
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}

