package com.alejandroguerra.bestof3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Nina {
    public static final float SPEED = 150f;

    Vector2 position;

    Animation rightAnimation;
    Animation leftAnimation;
    TextureRegion currentFrame;
    float stateTime;

    int lives;
    // Estados del personaje
    public enum State {
        RIGHT, LEFT,  IDLE
    }
    public State state;

    public Nina(float x, float y, int lives) {

        position = new Vector2(x, y);
        this.lives = lives;
        state = State.IDLE;

        // TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("animaciones.pack"));
        // Carga las animaciones para cada dirección
        rightAnimation = new Animation(0.25f,new TextureRegion[]{
                new Sprite( new Texture(Gdx.files.internal("nina/d1.png"))),
                new Sprite( new Texture(Gdx.files.internal("nina/d2.png"))),
                new Sprite( new Texture(Gdx.files.internal("nina/d3.png"))),
                new Sprite( new Texture(Gdx.files.internal("nina/d4.png"))),
                new Sprite( new Texture(Gdx.files.internal("nina/d5.png"))),
                new Sprite( new Texture(Gdx.files.internal("nina/d6.png")))});
        leftAnimation = new Animation(0.25f,new TextureRegion[]{
                new Sprite( new Texture(Gdx.files.internal("nina/i1.png"))),
                new Sprite( new Texture(Gdx.files.internal("nina/i2.png"))),
                new Sprite( new Texture(Gdx.files.internal("nina/i3.png"))),
                new Sprite( new Texture(Gdx.files.internal("nina/i4.png"))),
                new Sprite( new Texture(Gdx.files.internal("nina/i5.png"))),
                new Sprite( new Texture(Gdx.files.internal("nina/i6.png")))});
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