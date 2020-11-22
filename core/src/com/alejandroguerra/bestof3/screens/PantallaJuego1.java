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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class PantallaJuego1 implements Screen {

    SpriteBatch spriteBatch;
    BitmapFont fuente;
    Texture img;
    SpriteBatch batch;
    Oso oso;
    Nina niña;

    Array<Objeto> objetos;
    private Array<Muro> muro;
    long tiempoMuro;
    long tiempoMeteorito;
    boolean pause;
    BitmapFont fontvidas1;
    BitmapFont fontvidas2;
    BitmapFont pausa;
    private int contaVidas1=0;
    private int contaVidas2=0;
    Music aSound;

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        fuente = new BitmapFont();
       // Graphics.DisplayMode mode=Gdx.graphics.getDisplayMode();
       // Gdx.graphics.setFullscreenMode(mode);
        //Texture.setEnforcePotImages(false);

        batch = new SpriteBatch();
        img=new Texture("fondos/fondo1.png");
        //oso = new Oso(10, 2, 3);
        oso=new Oso(new Vector2(10,2),3);
        //niña = new Nina(Constantes.SCREEN_WIDTH, 2, 3);
        niña=new Nina(new Vector2(Constantes.SCREEN_WIDTH,2),3);
        objetos=new Array<>();
        muro=new Array<>();
        tiempoMeteorito= TimeUtils.millis();
        tiempoMuro = TimeUtils.millis();
        fontvidas1 = new BitmapFont();
        fontvidas1.setColor(Color.BLACK);
        fontvidas2 = new BitmapFont();
        fontvidas2.setColor(Color.BLUE);
        pausa = new BitmapFont();
        pausa.setColor(Color.WHITE);
        pausa.getData().setScale(3);
        contaVidas1=oso.getLives();
        contaVidas2=niña.getLives();
        aSound = Gdx.audio.newMusic(Gdx.files.internal("sonido/musicaJuego1.mp3"));
        aSound.play();
        aSound.setLooping(true);
        aSound.setVolume((float) 0.01);
    }

    @Override
    public void render(float delta) {
        float dt = Gdx.graphics.getDeltaTime();
        // Limpia la pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if(pause){
                resume();
            }else {
                pause();
            }
        }
        if (!pause) {
            Actualizar(dt);
        }
        pintar();

    }

    private void Actualizar(float dt) {

        if (Gdx.input.isKeyPressed(Keys.A)) {
            oso.state = Oso.State.LEFT;
            oso.move(new Vector2(-dt, 0));
        }
        else if (Gdx.input.isKeyPressed(Keys.D)) {
            oso.state = Oso.State.RIGHT;
            oso.move(new Vector2(dt, 0));
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
        Meteorito();
        moverMeteorito();
        colision();

    }



    public void Meteorito(){
        if(TimeUtils.millis() -tiempoMeteorito> MathUtils.random(1000,1000)){
            Texture textura = new Texture("purple_bullet.png");
            int x=MathUtils.random(0,Gdx.graphics.getWidth());
            int y= Gdx.graphics.getHeight() - textura.getHeight();

            Objeto roca = new Objeto(new Vector2(x,y),textura,1,4);
            objetos.add(roca);
            tiempoMeteorito=TimeUtils.millis();

        }

    }
    public void moverMeteorito() {
        for (Objeto roca : objetos) {
            roca.moverAbajo();
            if (roca.getPosicion().y < 90) {
                objetos.removeValue(roca, true);
            }
        }
    }
    public void colision() {
        for (Objeto roca : objetos) {
             if (oso.rect.overlaps(roca.rect)) {
                objetos.removeValue(roca, true);
                 oso.quitarVida();
                 Sound aSound = Gdx.audio.newSound(Gdx.files.internal("sonido/golpe.mp3"));
                 aSound.play();
            }
            if (niña.rect.overlaps(roca.rect)) {
                objetos.removeValue(roca, true);
                niña.quitarVida();
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
           // ((Game) Gdx.app.getApplicationListener()).setScreen(new Transicion());

        }
    }
    public void pintar(){
        batch.begin();
        batch.draw(img, 0, 0);

        for(Objeto rocas: objetos){
            rocas.mePinto(batch);
        }
        fontvidas1.draw(batch, "Vidas Jugador1: "+String.valueOf(contaVidas1), 100, 450);
        fontvidas2.draw(batch,"Vidas Jugador2: "+String.valueOf(contaVidas2),400,450);
        if(pause){
            pausa.draw(batch,"PAUSA",250,250);
        }
        batch.end();

        spriteBatch.begin();
        oso.render(spriteBatch);
        niña.render(spriteBatch);

        spriteBatch.end();
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
      //  spriteBatch.dispose();
        fuente.dispose();
    }
}