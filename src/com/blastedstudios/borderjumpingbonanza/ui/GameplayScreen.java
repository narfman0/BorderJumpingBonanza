package com.blastedstudios.borderjumpingbonanza.ui;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blastedstudios.borderjumpingbonanza.BorderJumpingBonanza;

public class GameplayScreen extends AbstractScreen {
	private SpriteBatch batch;
	private static HashMap<String,Texture> textureMap;

	public GameplayScreen(final BorderJumpingBonanza game){
		super(game);
		if(textureMap == null){
			textureMap = new HashMap<String, Texture>();
			textureMap.put("background", new Texture(Gdx.files.internal("data/textures/bjb_background.png"), Format.RGB565, true));
		}
		batch = new SpriteBatch();
	}

	@Override public void render(float arg0) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(textureMap.get("background"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
	}
}
