package com.alejandroguerra.bestof3.screens;


import com.alejandroguerra.bestof3.Bala;
import com.alejandroguerra.bestof3.Constantes;
import com.alejandroguerra.bestof3.Muro;
import com.alejandroguerra.bestof3.Personaje;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;

public class PantallaJuego2 implements Screen {

    private SpriteBatch batch;
    private Personaje jugador;
    private Personaje jugador2;
    private Array<Bala> balas1;
    private Array<Bala> balas2;
    private Array<Muro> muro1;
    private Array<Muro> muro2;
    private long tiempoJuego;
    private int movOso =1;
    private int movNina =1;
    private int contaVidas1=0;
    private int contaVidas2=0;
    private long tiempoDisparo1;
    private long tiempoDisparo2;
    BitmapFont fontvidas1;
    BitmapFont fontvidas2;
    private long tiempomuro1;
    private long tiempomuro2;
    Music aSound;
    Texture img;


    @Override
    public void show() {
        batch = new SpriteBatch();
        Graphics.DisplayMode mode=Gdx.graphics.getDisplayMode();
        Gdx.graphics.setFullscreenMode(mode);
        img=new Texture("fondos/fondo1.png");
        jugador = new Personaje(new Vector2(0,200),new Texture("oso/oso1.png"),3,4);
        jugador2 = new Personaje(new Vector2(Constantes.SCREEN_WIDTH,200),new Texture("nina/nina1.png"),3,4);
        contaVidas1=jugador.getVidas();
        contaVidas2=jugador2.getVidas();

        tiempoJuego = TimeUtils.millis();
        tiempomuro1 = TimeUtils.millis();
        tiempomuro2 = TimeUtils.millis();
        tiempoDisparo1 =  TimeUtils.millis();
        tiempoDisparo2 =  TimeUtils.millis();
        balas1 = new Array<>();
        balas2 = new Array<>();

        muro1 = new Array<>();
        muro2 = new Array<>();

        fontvidas1 = new BitmapFont();
        fontvidas1.setColor(Color.BLUE);
        fontvidas2 = new BitmapFont();
        fontvidas2.setColor(Color.BLUE);
    }

    @Override
    public void render(float delta) {
        actualizar();
        pintar();
    }
    private void actualizar(){
        // input de usuario
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            jugador.moverArriba();
            jugador.setTextura(new Texture("oso/oso1.png"));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            jugador2.moverArriba();
            jugador2.setTextura(new Texture("oso/oso1.png"));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            jugador2.moverAbajo();
            jugador2.setTextura(new Texture("oso/oso1.png"));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            jugador.moverAbajo();
            jugador.setTextura(new Texture("oso/oso1.png"));

        }
        if (jugador.getPosicion().y<0){
            jugador.setPosicion(new Vector2(0,0));
        }
        if (jugador2.getPosicion().y<0){
            jugador2.setPosicion(new Vector2(Constantes.SCREEN_WIDTH,0));
        }
        if (jugador.getPosicion().y>Constantes.SCREEN_HEIGHT){
            jugador.setPosicion(new Vector2(0,Constantes.SCREEN_HEIGHT));
        }
        if (jugador2.getPosicion().y>Constantes.SCREEN_HEIGHT){
            jugador2.setPosicion(new Vector2(Constantes.SCREEN_WIDTH,Constantes.SCREEN_HEIGHT));
        }


        comprobarColisiones();
        disparar();
        moverBalas();
        muro();

    }

    private  void muro(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            if(TimeUtils.millis()- tiempomuro1> Constantes.TIEMPO_ENTRE_MURO){
                int x = (int) (jugador.getPosicion().x + jugador.getTextura().getWidth());
                int y = (int) jugador.getPosicion().y;
                Muro muro = new Muro(new Vector2(x, y), new Texture("Muro.png"), 1, 0);
                muro1.add(muro);
                tiempomuro1=TimeUtils.millis();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            if(TimeUtils.millis()- tiempomuro2> Constantes.TIEMPO_ENTRE_MURO){
                int x = (int) (jugador2.getPosicion().x-50);
                int y = (int) jugador2.getPosicion().y;
                Muro muro = new Muro(new Vector2(x, y), new Texture("Muro.png"), 1, 0);
                muro2.add(muro);
                tiempomuro2=TimeUtils.millis();
            }
        }
    }

    private void  disparar(){

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if(TimeUtils.millis()- tiempoDisparo1> Constantes.TIEMPO_ENTRE_DISPAROS){
                int x = (int) (jugador.getPosicion().x + jugador.getTextura().getWidth());
                int y = (int) jugador.getPosicion().y;
                Bala bala = new Bala(new Vector2(x, y+50), new Texture("espada1.png"), 1, 5);
                balas1.add(bala);
                tiempoDisparo1=TimeUtils.millis();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_0)) {
            if(TimeUtils.millis()- tiempoDisparo2> Constantes.TIEMPO_ENTRE_DISPAROS){
                int x = (int) (jugador2.getPosicion().x-50);
                int y = (int) jugador2.getPosicion().y;
                Bala bala = new Bala(new Vector2(x, y+50), new Texture("espada2.png"), 1, 5);
                balas2.add(bala);
                tiempoDisparo2=TimeUtils.millis();
            }
        }
    }
    private void moverBalas(){
        if(balas1.size>0){
            for(Bala bala : balas1){
                bala.moverDerecha();
                if (bala.getPosicion().x > Constantes.SCREEN_WIDTH) {
                    balas1.removeValue(bala, true);
                }
            }

        }
        if(balas2.size>0){
            for(Bala bala : balas2){
                bala.moverIzquierda();
                if (bala.getPosicion().x < 0) {
                    balas2.removeValue(bala, true);
                }
            }
        }
    }



    private void comprobarColisiones(){


            for(Bala bala : balas1){
                if (bala.rect.overlaps(jugador2.rect)) {
                    jugador2.quitarVida();
                    balas1.removeValue(bala,true);
                    }
            }
        for(Bala bala : balas2){
            if (bala.rect.overlaps(jugador.rect)) {
                jugador.quitarVida();
                balas2.removeValue(bala,true);
            }
        }
        for(Muro muro : muro1){
            for(Bala bala : balas2){
                if (bala.rect.overlaps(muro.rect)) {
                    muro.quitarVida();
                    if(muro.getVidas()<=0){
                        balas2.removeValue(bala,true);
                        muro1.removeValue(muro,true);
                    }
                }
            }
        }

        for(Muro muro : muro2){
            for(Bala bala : balas1){
                if (bala.rect.overlaps(muro.rect)) {
                    muro.quitarVida();
                    if(muro.getVidas()<=0){
                        balas1.removeValue(bala,true);
                        muro2.removeValue(muro,true);
                    }
                }
            }
        }

        contaVidas1=jugador.getVidas();
        contaVidas2=jugador2.getVidas();
        }
    public int cambiaTexturaOso(Personaje jugador,int i){

        switch (i){
            case 1:jugador.setTextura(new Texture("oso/oso1.png"));
                break;
            case 2: jugador.setTextura(new Texture("oso/oso2.png"));
                break;
            case 3: jugador.setTextura(new Texture("oso/oso3.png"));
                break;
            case 4:jugador.setTextura(new Texture("oso/oso4.png"));
        }
        i++;
        if(i>4){
            i=1;
        }
        return i;
    }
    public int cambiaTexturaNina(Personaje jugador2,int i){

        switch (i){
            case 1:jugador2.setTextura(new Texture("nina/nina1.png"));
                break;
            case 2: jugador2.setTextura(new Texture("nina/nina2.png"));
                break;
            case 3: jugador2.setTextura(new Texture("nina/nina3.png"));
                break;
            case 4:jugador2.setTextura(new Texture("nina/nina4.png"));
        }
        i++;
        if(i>4){
            i=1;
        }
        return i;
    }

    private  void pintar(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        movOso=cambiaTexturaOso(jugador,movOso);
        movNina=cambiaTexturaNina(jugador2,movNina);
        jugador.mePinto(batch);
        jugador2.mePinto(batch);
        fontvidas1.draw(batch, "Vidas: "+String.valueOf(contaVidas1), 30, 450);
        fontvidas2.draw(batch,"Vidas "+String.valueOf(contaVidas2),90,450);


        for (Bala bala : balas1){
            bala.mePinto(batch);
        }

        for (Bala bala : balas2){
            bala.mePinto(batch);
        }
        for (Muro muro : muro1){
            muro.mePinto(batch);
        }
        for (Muro muro : muro2){
            muro.mePinto(batch);
        }
        batch.end();
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
        batch.dispose();
    }
}
