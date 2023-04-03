package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Apple {
    float x, y, n, size;

    public Apple(int n, int size) {
        this.n = n;
        this.size = size;
        setPosition();
    }

    public void setPosition() {
        this.x = (int) MathUtils.random(2, n - 2);
        this.y = (int) MathUtils.random(2, n - 2);
    }

    public void draw(ShapeRenderer sr) {

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.RED);
        sr.ellipse(x * size, y * size, size - 5, size - 5);
        sr.end();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
