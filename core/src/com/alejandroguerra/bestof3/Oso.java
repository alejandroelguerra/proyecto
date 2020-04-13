package com.alejandroguerra.bestof3;

import com.alejandroguerra.bestof3.Constantes;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Oso {

    public static final float SPEED = 150f;

    Vector2 position;
    Animation rightAnimation;
    Animation leftAnimation;
    TextureRegion currentFrame;
    float stateTime;
    public Rectangle rect;
    int lives;

    // Estados del personaje
    public enum State {
        RIGHT, LEFT,  IDLE
    }
    public State state;

    public Oso(float x, float y, int lives) {

        position = new Vector2(x, y);
        this.lives = lives;
        state = State.IDLE;


        rightAnimation = new Animation(0.25f,new TextureRegion[]{
                new Sprite( new Texture(Gdx.files.internal("oso/d0.png"))),
                new Sprite( new Texture(Gdx.files.internal("oso/d1.png"))),
                new Sprite( new Texture(Gdx.files.internal("oso/d2.png"))),
                new Sprite( new Texture(Gdx.files.internal("oso/d3.png"))),
                new Sprite( new Texture(Gdx.files.internal("oso/d4.png"))),
                new Sprite( new Texture(Gdx.files.internal("oso/d5.png")))});
        leftAnimation = new Animation(0.25f,new TextureRegion[]{
                new Sprite( new Texture(Gdx.files.internal("oso/i0.png"))),
                new Sprite( new Texture(Gdx.files.internal("oso/i1.png"))),
                new Sprite( new Texture(Gdx.files.internal("oso/i2.png"))),
                new Sprite( new Texture(Gdx.files.internal("oso/i3.png"))),
                new Sprite( new Texture(Gdx.files.internal("oso/i4.png"))),
                new Sprite( new Texture(Gdx.files.internal("oso/i5.png")))});
    }

    // Desplaza la tabla en el eje x
    public void move(Vector2 movement) {

        movement.scl(SPEED);
        position.add(movement);
    }

    public void render(SpriteBatch batch) {

        batch.draw(currentFrame, position.x, position.y);
    }

    /**
     * Actualiza el estado del personaje
     * @param dt
     */
    public void update(float dt) {

        // Calcula el tiempo para cargar el frame que proceda de la animación
        stateTime += dt;

        // Carga el frame según su posición y el momento del juego
        switch (state) {
            case RIGHT:
                currentFrame = (TextureRegion) rightAnimation.getKeyFrame(stateTime, true);
                break;
            case LEFT:
                currentFrame = (TextureRegion) leftAnimation.getKeyFrame(stateTime, true);
                break;
            case IDLE:
                currentFrame = (TextureRegion) rightAnimation.getKeyFrame(0, true);
                break;
            default:
                currentFrame = (TextureRegion) rightAnimation.getKeyFrame(0, true);
        }

        // Comprueba los límites de la pantalla
        if (position.x <= 0)
            position.x = 0;

        if ((position.x + currentFrame.getRegionWidth()) >= Constantes.SCREEN_WIDTH)
            position.x = Constantes .SCREEN_WIDTH - currentFrame.getRegionWidth();
    }
}