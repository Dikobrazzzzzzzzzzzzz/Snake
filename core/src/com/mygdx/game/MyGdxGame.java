package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayDeque;

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

    @Override
    public void create() {
        speed = 1;
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        apple = new Apple(N, SIZE_N);
        snake = new Snake(N, SIZE_N);
        snake.setHead(true);
        belochka.addFirst(snake);
    }

    @Override
    public void render() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.ORANGE);
        sr.rect(0, 0, SIZE, SIZE);
        sr.end();
        for (Snake sn : belochka) {
            sn.draw(sr);
        }

        apple.draw(sr);
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            for (Snake sn : belochka) {
                sn.pressW();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            for (Snake sn : belochka) {
                sn.pressS();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            for (Snake sn : belochka) {
                sn.pressD();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            for (Snake sn : belochka) {
                sn.pressA();
            }
        }


        Snake head = belochka.getFirst();
        float x = head.getX();
        float y = head.getY();
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
