package com.alejandroguerra.bestof3.screens;


import com.alejandroguerra.bestof3.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class PantallaJuego3 implements Screen {

    SpriteBatch spriteBatch;
    BitmapFont fuente;
    BitmapFont pausa;
    Texture img;
    SpriteBatch batch;
    Oso oso;
    Nina niña;
    Array<Objeto> objetos;
    private Array<Muro> muro;
    long tiempoMuro;
    long tiempoMeteorito;
    boolean pause;
    boolean salto1;
    boolean salto2;
    BitmapFont fontvidas1;
    BitmapFont fontvidas2;
    private int contaVidas1=0;
    private int contaVidas2=0;
    Music aSound;


    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        fuente = new BitmapFont();

        batch = new SpriteBatch();
        img=new Texture("fondos/fondo4.png");
        oso=new Oso(new Vector2(10,2),3);
        niña=new Nina(new Vector2(10,90),3);
        objetos=new Array<>();
        muro=new Array<>();
        tiempoMeteorito= TimeUtils.millis();
        tiempoMuro = TimeUtils.millis();
        pausa = new BitmapFont();
        pausa.setColor(Color.BLACK);
        pausa.getData().setScale(3);
        salto1=true;
        salto2=true;
        fontvidas1 = new BitmapFont();
        fontvidas1.setColor(Color.BLACK);
        fontvidas2 = new BitmapFont();
        fontvidas2.setColor(Color.BLACK);
        contaVidas1=oso.getLives();
        contaVidas2=niña.getLives();
        aSound = Gdx.audio.newMusic(Gdx.files.internal("sonido/musicaJuego3.mp3"));
        aSound.play();
        aSound.setLooping(true);
        aSound.setVolume((float) 0.05);
    }

    @Override
    public void render(float delta) {
        float dt = Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if(pause){
                resume();
            }else {
                pause();
            }
        }
        if(!pause) {
            actualizar(dt);
        }
        pintar();
    }

    private void pintar() {
        // Limpia la pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);

        for(Objeto rocas: objetos){
            rocas.mePinto(batch);
        }
        if(pause){
            pausa.draw(batch,"PAUSA",250,250);
        }
        fontvidas1.draw(batch, "Vidas Jugador1: "+String.valueOf(contaVidas1), 100, 450);
        fontvidas2.draw(batch,"Vidas Jugador2: "+String.valueOf(contaVidas2),400,450);
        batch.end();

        spriteBatch.begin();
        oso.render(spriteBatch);
        niña.render(spriteBatch);
        spriteBatch.end();
    }

    private void actualizar(float dt) {

        if (Gdx.input.isKeyPressed(Keys.A)) {
            oso.state = Oso.State.LEFT;
            oso.move(new Vector2(-dt, 0));
        }
        else if (Gdx.input.isKeyPressed(Keys.D)) {
            oso.state = Oso.State.RIGHT;
            oso.move(new Vector2(dt, 0));
        }
        else if (Gdx.input.isKeyPressed(Keys.UP)) {
            if(salto2) {
                niña.jump();
                salto2=false;
            }
        }
        else if (Gdx.input.isKeyPressed(Keys.W)) {
            if(salto1) {
                oso.jump();
                salto1 = false;
            }
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            niña.state = Nina.State.LEFT;
            niña.move(new Vector2(-dt, 0));
        }
        else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            niña.state = Nina.State.RIGHT;
            niña.move(new Vector2(dt, 0));
        }
        oso.update(dt);
        niña.update(dt);
        Cactus();
        moverCactus();
        colision();

        Vector2 posnina=niña.getPosicion();
        if(posnina.y==90){
            salto2=true;
        }
        Vector2 pososo=oso.getPosicion();
        if(pososo.y==90){
            salto1=true;
        }

    }



    public void Cactus(){
        if(TimeUtils.millis() -tiempoMeteorito> MathUtils.random(3000,3500)){
            Texture textura = new Texture("captus.png");

            int x=Gdx.graphics.getWidth() ;
            int y= 90;

            Objeto cactus = new Objeto(new Vector2(x,y),textura,1,11);
            objetos.add(cactus);
            tiempoMeteorito=TimeUtils.millis();

        }

    }
    public void moverCactus() {
        for (Objeto cactus : objetos) {
            cactus.moverIzquierda();
            if (cactus.getPosicion().y < 10) {
                objetos.removeValue(cactus, true);
            }
        }
    }
    public void colision() {
        for (Objeto cactus : objetos) {
            if (niña.rect.overlaps(cactus.rect)) {
                objetos.removeValue(cactus, true);
                niña.quitarVida();
                Sound aSound = Gdx.audio.newSound(Gdx.files.internal("sonido/golpe.mp3"));
                aSound.play();
            }
            if (oso.rect.overlaps(cactus.rect)) {
                objetos.removeValue(cactus, true);
                oso.quitarVida();
                Sound aSound = Gdx.audio.newSound(Gdx.files.internal("sonido/golpe.mp3"));
                aSound.play();
            }
        }
        contaVidas1=oso.getLives();
        contaVidas2=niña.getLives();
        if(contaVidas1<=0){
            Util.score2=Util.score2+1;
            aSound.stop();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Transicion());
        }
        if(contaVidas2<=0){
            Util.score1=Util.score1+1;
            aSound.stop();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Transicion());

        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        pause=true;
    }

    @Override
    public void resume() {
        pause=false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        fuente.dispose();
    }
}