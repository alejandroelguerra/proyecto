package com.alejandroguerra.bestof3.screens;

import com.alejandroguerra.bestof3.Util;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.VisUI;

public class PantallaMenu  implements Screen {

    Stage stage;
    Texture img;
    SpriteBatch batch;
    Music aSound;
    @Override
    public void show() {
        if (!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();
        batch= new SpriteBatch();
        img=new Texture("fondos/fondo2(1).png");
        aSound = Gdx.audio.newMusic(Gdx.files.internal("sonido/menu.mp3"));
        aSound.play();
        aSound.setLooping(true);
        aSound.setVolume((float) 0.1);
        Util.score1=0;
        Util.score2=0;

    }

    @Override
    public void render(float dt) {
        stage.getBatch().begin();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Pinta la UI en la pantalla
        stage.getBatch().draw(img, 0, 0, stage.getWidth(),stage.getHeight());
        stage.getBatch().end();
        stage.act(dt);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaJuego2());
            aSound.stop();
            dispose();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.exit(0);
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
