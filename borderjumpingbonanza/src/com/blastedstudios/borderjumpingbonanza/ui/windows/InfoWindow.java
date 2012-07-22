package com.blastedstudios.borderjumpingbonanza.ui.windows;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.blastedstudios.borderjumpingbonanza.ui.GameplayScreen;

public class InfoWindow extends Window {
	private Label levelLabel, scoreLabel, timeLabel, livesLabel;
	private final GameplayScreen screen;
	
	public InfoWindow(final Skin skin, final GameplayScreen screen){
		super(skin);
		this.screen = screen;
		add(levelLabel = new Label("", skin)).pad(6);
		add(scoreLabel = new Label("", skin)).pad(6);
		add(timeLabel = new Label("", skin)).pad(6);
		add(livesLabel = new Label("", skin)).pad(6);
		pack();
		width = 300;
		height = 40;
		y = Gdx.graphics.getHeight()-16;
		new Timer("Update Info Thread", true).scheduleAtFixedRate(new UpdateLabelTimer(), 0, 100);
	}
	
	private void updateLabels(){
		levelLabel.setText("Level: " + screen.getLevel());
		scoreLabel.setText("Score: " + screen.getScore());
		timeLabel.setText("Time: " + screen.getTimeRemaining());
		livesLabel.setText("Lives: " + screen.getLives());
	}
	
	private class UpdateLabelTimer extends TimerTask{
		@Override public void run() {
			updateLabels();
		}
	}
}
