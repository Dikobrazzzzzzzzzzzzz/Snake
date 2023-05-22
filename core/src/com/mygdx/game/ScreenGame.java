package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayDeque;

public class ScreenGame extends Game {
    public static final int SIZE = 800;
    public static final int N = 16;
    public static final int SIZE_N = SIZE / N;
    public static final Object SCR_WIDTH = 1;
    public static final Object SCR_HEIGHT = 2;
    public int speed;
    public ArrayDeque<Snake> belochka = new ArrayDeque<Snake>();
    public Snake snake;
    public Apple apple;
    SpriteBatch batch;
    ShapeRenderer sr;
    Texture img;
    long timegame;
    long timeStart;


    @Override
    public void create() {
        timeStart = TimeUtils.millis();
        speed = 1;
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        apple = new Apple(N, SIZE_N);
        snake = new Snake(N, SIZE_N);
        snake.setHead(true);
        belochka.addFirst(snake);
        timegame = TimeUtils.millis();
        setScreen(new ScreenIntro (this));
    }
}
