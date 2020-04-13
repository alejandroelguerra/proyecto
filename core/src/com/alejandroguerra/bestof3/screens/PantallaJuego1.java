package com.alejandroguerra.bestof3.screens;


import com.alejandroguerra.bestof3.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Clase principal del proyecto
 * @author Santiago Faci
 * @version curso 2014-2015
 */
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


    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        fuente = new BitmapFont();
        Graphics.DisplayMode mode=Gdx.graphics.getDisplayMode();
        Gdx.graphics.setFullscreenMode(mode);
        //Texture.setEnforcePotImages(false);

        batch = new SpriteBatch();
        img=new Texture("fondos/fondo1.png");
        oso = new Oso(10, 2, 3);
        niña = new Nina(500, 2, 3);
        objetos=new Array<>();
        muro=new Array<>();
        tiempoMeteorito= TimeUtils.millis();
        tiempoMuro = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        float dt = Gdx.graphics.getDeltaTime();

        // Limpia la pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Actualizar(dt);
        batch.begin();
        batch.draw(img, 0, 0);

        for(Objeto rocas: objetos){
            rocas.mePinto(batch);
        }
        batch.end();

        spriteBatch.begin();
        oso.render(spriteBatch);
        niña.render(spriteBatch);

        spriteBatch.end();
    }

    private void Actualizar(float dt) {

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            oso.state = Oso.State.LEFT;
            oso.move(new Vector2(-dt, 0));
        }
        else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            oso.state = Oso.State.RIGHT;
            oso.move(new Vector2(dt, 0));
        }
       /* else if (Gdx.input.isKeyPressed(Keys.UP)) {
            oso.state = b.State.UP;
            oso.move(new Vector2(0, dt));
        }
        else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            oso.state = b.State.DOWN;
            oso.move(new Vector2(0, -dt));
        }*/
        /*else
            oso.state = b.State.IDLE;*/

        if (Gdx.input.isKeyPressed(Keys.A)) {
            niña.state = Nina.State.LEFT;
            niña.move(new Vector2(-dt, 0));
        }
        else if (Gdx.input.isKeyPressed(Keys.D)) {
            niña.state = Nina.State.RIGHT;
            niña.move(new Vector2(dt, 0));
        }
        //else
            //niña.state = c.State.IDLE;
        oso.update(dt);
        niña.update(dt);
        Meteorito();
        moverMeteorito();


    }



    public void Meteorito(){
        if(TimeUtils.millis() -tiempoMeteorito> MathUtils.random(1000,3000)){
            Texture textura = new Texture("purple_bullet.png");
            int x=MathUtils.random(0,Gdx.graphics.getWidth());
            int y= Gdx.graphics.getHeight() - textura.getHeight();

            Objeto roca = new Objeto(new Vector2(x,y),textura,1,2);
            objetos.add(roca);
            tiempoMeteorito=TimeUtils.millis();

        }

    }
    public void moverMeteorito() {
        for (Objeto roca : objetos) {
            roca.moverAbajo();
            if (roca.getPosicion().y < 10) {
                objetos.removeValue(roca, true);
            }
        }
    }
    public void colision() {
        for (Objeto roca : objetos) {
            if (oso.rect.overlaps(roca.rect)) {
                objetos.removeValue(roca, true);

            }
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

    /*
     * Método invocado cuando se destruye la aplicación
     * Siempre va precedido de una llamada a 'pause()'
     */
    @Override
    public void dispose() {
        spriteBatch.dispose();
        fuente.dispose();
    }
}