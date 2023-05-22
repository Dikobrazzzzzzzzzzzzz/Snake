package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class ScreenIntro implements Screen {
    Texture jpg;
    ScreenGame game;

    public ScreenIntro(ScreenGame myGdxGame) {
        game = myGdxGame;
    }

    @Override
    public void show() {
        jpg = new Texture ("zmeya.jpg");

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(jpg, 0, 0, game.SIZE, game.SIZE);
        BitmapFont text = new BitmapFont();
        text.setColor(Color.WHITE);
        text.draw(game.batch, "Hello maybe you play this game!?", 100 , 150);
        text.draw(game.batch, "Tape 'space' to start in the game 'Snake' ", 115, 250);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new MyGdxGame(game));
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
