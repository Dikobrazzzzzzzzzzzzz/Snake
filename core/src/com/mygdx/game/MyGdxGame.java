package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayDeque;
import java.util.Objects;

public class MyGdxGame extends ScreenAdapter {
    private int speed;
    private ArrayDeque<Snake> belochka;
    private Apple apple;
    SpriteBatch batch;
    ShapeRenderer sr;
    Texture img;
    long timegame;
    long timeStart;

    ScreenGame game;

    public MyGdxGame(ScreenGame game) {
        this.game = game;
        apple = game.apple;
        timeStart = game.timeStart;
        speed = game.speed;
        batch = game.batch;
        sr = game.sr;
        belochka = new ArrayDeque<>(game.belochka);
        timegame = game.timegame;
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
        if (y >= game.N-1){
            y_N = -1;
        }
        if (x >= game.N-1){
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
            Snake zmeya = new Snake(game.N, game.SIZE_N);
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
    public void render(float delta) {
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
            Snake zmeya = new Snake(game.N, game.SIZE_N);
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
        sr.rect(0, 0, game.SIZE, game.SIZE);
        sr.end();
        for (Snake sn : belochka) {
            sn.draw(sr);
        }
        apple.draw(sr);

        long timeCurrent = TimeUtils.millis() - timeStart;
        BitmapFont text = new BitmapFont();
        text.setColor(Color.BLACK);
        batch.begin();
        text.draw(batch, time_string(timeCurrent) + " score: " +speed,(game.N-3)*game.SIZE_N, (game.N)*game.SIZE_N); // время

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

