package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
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
    private int speed = 1;
    private ArrayDeque<Snake> belochka = new ArrayDeque<Snake>();
    private Snake snake;
    private Apple apple;
    SpriteBatch batch;
    ShapeRenderer sr;
    Texture img;

    @Override
    public void create() {
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        apple = new Apple (N, SIZE_N);
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
        for(Snake sn: belochka){
            sn.draw(sr);
        }
        apple.draw(sr);
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
