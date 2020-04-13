package com.alejandroguerra.bestof3;

import com.alejandroguerra.bestof3.screens.PantallaMenu;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MyGdxGame extends Game {
	
	@Override
	public void create () {
		((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaMenu());
	}

	@Override
	public void render () {super.render();
	}
	
	@Override
	public void dispose () {

	}
}
