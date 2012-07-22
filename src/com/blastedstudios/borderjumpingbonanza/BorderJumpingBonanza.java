package com.blastedstudios.borderjumpingbonanza;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.blastedstudios.borderjumpingbonanza.ui.MainScreen;

public class BorderJumpingBonanza extends Game {
	public static Random random = new Random();

	@Override public void create () {
		setScreen(new MainScreen(this));
	}
}
