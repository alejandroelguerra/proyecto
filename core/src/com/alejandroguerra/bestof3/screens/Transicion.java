package com.alejandroguerra.bestof3.screens;

import com.alejandroguerra.bestof3.Util;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.VisUI;

public class Transicion implements Screen {
    Stage stage;
    Texture img;
    SpriteBatch batch;
    BitmapFont puntos;
    BitmapFont opciones;
    BitmapFont ganador;
    @Override
    public void show() {
        if (!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();
        batch= new SpriteBatch();
        img=new Texture("fondos/transicion.png");
        puntos = new BitmapFont();
        opciones= new BitmapFont();
        ganador= new BitmapFont();
        puntos.getData().setScale(5);
        puntos.setColor(Color.WHITE);
        opciones.getData().setScale(1);
        opciones.setColor(Color.WHITE);
        ganador.getData().setScale(3);
        ganador.setColor(Color.WHITE);
    }

    @Override
    public void render(float dt) {
        batch.begin();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Pinta la UI en la pantalla
        batch.draw(img, 0, 0, stage.getWidth(),stage.getHeight());
        puntos.draw(batch, String.valueOf(Util.score1) +":"+String.valueOf(Util.score2), 260, 290);
        opciones.draw(batch,"ENTER para continuar\n\nESCAPE para salir",220,180);
        if(Util.score1==2){
            ganador.draw(batch,"JUGADOR 1 GANA",100,400);
        }
        if(Util.score2==2){
            ganador.draw(batch,"JUGADOR 2 GANA",100,400);
        }
        batch.end();
        stage.act(dt);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            if(Util.score1==2 || Util.score2==2) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaMenu());
                dispose();
            }
            if(Util.score1==1 && Util.score2==1){
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaJuego3());
                dispose();
            }
            if(Util.score1==1 && Util.score2==0||Util.score1==0 && Util.score2==1){
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaJuego1());
            }
            /*else {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaJuego1());
                dispose();
            }*/
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.exit(0);
        }

    }

    @Override
    public void resize(int i, int i1) {

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
    batch.dispose();
    }
}
