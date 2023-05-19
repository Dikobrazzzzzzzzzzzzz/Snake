package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayDeque;
import java.util.Objects;

public class MyGdxGame extends ApplicationAdapter {
    public static final int SIZE = 800;
    private static final int N = 16;
    private static final int SIZE_N = SIZE / N;
    private static final Object SCR_WIDTH = 1;
    private static final Object SCR_HEIGHT = 2;
    private int speed;
    private ArrayDeque<Snake> belochka = new ArrayDeque<Snake>();
    private Snake snake;
    private Apple apple;
    SpriteBatch batch;
    ShapeRenderer sr;
    Texture img;
    InputKeyboard keyboard;
    long timegame;
    long timeStart;

    ScreenIntro screenIntro;
    ScreenGame screenGame;
    ScreenSettings screenSettings;
    ScreenAbout screenAbout;

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

        keyboard = new InputKeyboard(SCR_WIDTH, SCR_HEIGHT, 10);

        screenIntro = new ScreenIntro(this);
        screenGame = new ScreenGame(this);
        screenSettings = new ScreenSettings(this);
        screenAbout = new ScreenAbout(this);

        setScreen(screenIntro);
    }

    private void setScreen(ScreenIntro screenIntro) {
    }

    public void update(float x, float y, int dx, int dy) {
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
        for (Snake sn : belochka) {
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

    private void input_btn(int btn, String press) {
        Snake head = belochka.getFirst();
        float x = head.getX();
        float y = head.getY();
        if (Gdx.input.isKeyJustPressed(btn)) {
            Snake zmeya = new Snake(N, SIZE_N);
            zmeya.setHead(false);
            zmeya.setPosition(x, y);
            if (Objects.equals(press, "W")) {
                zmeya.pressW();
            } else if (Objects.equals(press, "A")) {
                zmeya.pressA();
            } else if (Objects.equals(press, "S")) {
                zmeya.pressS();
            } else if (Objects.equals(press, "D")) {
                zmeya.pressD();
            }

            update(zmeya.getX(), zmeya.getY(), zmeya.getDx(), zmeya.getDy());
            timegame = TimeUtils.millis();
        }

        }

    @Override
    public void render() {
        cordinat();
        draw();

        Snake head = belochka.getFirst();
        float x = head.getX();
        float y = head.getY();


        if ((TimeUtils.millis() - timegame) > 2000 / speed) {
            float x_SN = x + head.getDx();
            float y_SN = y + head.getDy();
            int dx = head.getDx();
            int dy = head.getDy();
            update(x_SN, y_SN, dx, dy);
            timegame = TimeUtils.millis();
        }

        input_btn(Input.Keys.W, "W");
        input_btn(Input.Keys.S, "S");
        input_btn(Input.Keys.A, "A");
        input_btn(Input.Keys.D, "D");


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

    private void draw() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.ORANGE);
        sr.rect(0, 0, SIZE, SIZE);
        sr.end();
        for (Snake sn : belochka) {
            sn.draw(sr);
        }
        apple.draw(sr);

        long timeCurrent = TimeUtils.millis() - timeStart;
        BitmapFont text = new BitmapFont();
        text.setColor(Color.BLACK);
        batch.begin();
        text.draw(batch, time_string(timeCurrent) + " score: " +speed,(N-3)*SIZE_N, (N)*SIZE_N); // время

        batch.end();
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

    String time_string(long time) {
        String minutes = "" + time / 1000 / 60 / 10 + time / 1000 / 60 % 10;
        String sec = "" + time / 1000 % 60 / 10 + time / 1000 % 60 % 10;
        return minutes+":"+sec;
    }
}

