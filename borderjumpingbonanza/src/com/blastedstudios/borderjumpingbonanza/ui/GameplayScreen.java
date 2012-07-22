package com.blastedstudios.borderjumpingbonanza.ui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.borderjumpingbonanza.BorderJumpingBonanza;

public class GameplayScreen extends AbstractScreen {
	private SpriteBatch batch;
	private static Texture background, mexican, truck, cutter, barge;
	private Vector2 location, lastTouched;
	private boolean touchMove;
	private static float MOVEMENT_MODIFIER = .2f, ROWS = 10;
	private int difficulty = 3;
	private ArrayList<Being> enemies;

	public GameplayScreen(final BorderJumpingBonanza game){
		super(game);
		if(background == null){
			background =  new Texture(Gdx.files.internal("data/textures/bjb_background.png"), Format.RGBA8888, true);
			mexican = new Texture(Gdx.files.internal("data/textures/mexican.png"), Format.RGBA8888, true);
			truck = new Texture(Gdx.files.internal("data/textures/truck.png"), Format.RGBA8888, true);
			cutter = new Texture(Gdx.files.internal("data/textures/cutter.png"), Format.RGBA8888, true);
			barge = new Texture(Gdx.files.internal("data/textures/barge.png"), Format.RGBA8888, true);
		}
		batch = new SpriteBatch();
		newLevel();
	}

	@Override public void render(float delta) {
		update(delta);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(mexican, location.x/100 * Gdx.graphics.getWidth() - 18, 
				location.y/100 * Gdx.graphics.getHeight() - 18, 38, 45);
		for(Being enemy : enemies)
			batch.draw(enemy.texture, enemy.location.x/100 * Gdx.graphics.getWidth() - 18, 
					enemy.location.y/100 * Gdx.graphics.getHeight() - 18, 36, 36);
		batch.end();
	}

	private void update(float delta) {
		Vector2 movement = new Vector2();
		if(Gdx.input.isKeyPressed(Keys.UP))
			movement.y += 1;
		else if(Gdx.input.isKeyPressed(Keys.DOWN))
			movement.y -= 1;
		if(Gdx.input.isKeyPressed(Keys.LEFT))
			movement.x -= 1;
		else if(Gdx.input.isKeyPressed(Keys.RIGHT))
			movement.x += 1;
		else if(Gdx.input.isKeyPressed(Keys.F2))
			newLevel();
		if(Gdx.input.isTouched()){
			lastTouched = new Vector2(Gdx.input.getX()*100f/Gdx.graphics.getWidth(), 
					(Gdx.graphics.getHeight()-Gdx.input.getY())*100f/Gdx.graphics.getHeight());
			touchMove = true;
		}
		if(touchMove){
			if(lastTouched.dst2(location) > 1)
				movement = lastTouched.tmp().sub(location).nor();
			else
				touchMove = false;
		}
		movement.nor();
		location.add(movement.mul(MOVEMENT_MODIFIER));
		location.x = Math.max(10, Math.min(90, location.x));
		location.y = Math.max(10, location.y);
		
		for(Being enemy : enemies){
			enemy.location.x += ((enemy.isLeft ? -1 : 1) * MOVEMENT_MODIFIER);
			if(enemy.location.x < 10)
				enemy.isLeft = false;
			if(enemy.location.x > 90)
				enemy.isLeft = true;
		}
		
		if(location.y > 95){
			++difficulty;
			newLevel();
		}
	}
	
	private void newLevel(){
		touchMove = false;
		enemies = new ArrayList<Being>();
		for(int i=0; i<difficulty; i++){
			int row = BorderJumpingBonanza.random.nextInt((int)ROWS);
			Texture texture = row < ROWS/2 ? truck : BorderJumpingBonanza.random.nextBoolean() ? barge : cutter;
			Vector2 location = new Vector2(5 + BorderJumpingBonanza.random.nextFloat()*85, 7.14f*row + (row<ROWS/2?18:25));
			enemies.add(new Being(texture, location));
		}
		location = new Vector2(50, 10);
	}
	
	private class Being{
		public Vector2 location;
		public final Texture texture;
		public boolean isLeft;
		
		public Being(Texture texture, Vector2 location){
			this.location = location;
			this.texture = texture;
			isLeft = BorderJumpingBonanza.random.nextBoolean();
		}
	}
}
