package com.alejandroguerra.bestof3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Objeto extends  Personaje {
    public Objeto(Vector2 posicion, Texture textura, int vidas, int velocidad) {
        super(posicion, textura, vidas, velocidad);
    }
}
