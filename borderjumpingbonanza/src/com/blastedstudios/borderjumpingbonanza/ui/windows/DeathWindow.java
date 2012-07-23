package com.blastedstudios.borderjumpingbonanza.ui.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.blastedstudios.borderjumpingbonanza.game.Scores;
import com.blastedstudios.borderjumpingbonanza.ui.GameplayScreen;
import com.blastedstudios.borderjumpingbonanza.ui.HighScoreScreen;
import com.blastedstudios.borderjumpingbonanza.ui.MainScreen;

public class DeathWindow extends Window {
	private final TextField nameTextfield;
	
	public DeathWindow(final Skin skin, final GameplayScreen screen){
		super("Death", skin);
		final long lives = screen.getLives(), score = screen.getScore();
		nameTextfield = new TextField("Anonymous", skin);
		final Button okButton = new TextButton("Ok", skin.getStyle(TextButtonStyle.class), "ok");
		final Button noButton = new TextButton("No", skin.getStyle(TextButtonStyle.class), "no");
		final Button exitButton = new TextButton("Exit", skin.getStyle(TextButtonStyle.class), "exit");
		okButton.setClickListener(new ClickListener() {
			@Override public void click(Actor actor, float arg1, float arg2) {
				actor.getStage().removeActor(actor.parent);
				if(lives <= 0){
					Scores.submitScore(nameTextfield.getText(), score);
					screen.game.setScreen(new HighScoreScreen(screen.game));
				}
				screen.newLevel();
			}
		});
		noButton.setClickListener(new ClickListener() {
			@Override public void click(Actor actor, float arg1, float arg2) {
				actor.getStage().removeActor(actor.parent);
				screen.newLevel();
			}
		});
		exitButton.setClickListener(new ClickListener() {
			@Override public void click(Actor arg0, float arg1, float arg2) {
				screen.game.setScreen(new MainScreen(screen.game));
			}
		});
		if(screen.getTimeRemaining() <= 0)
			add(new Label("You have run out of time!", skin));
		else if(lives <= 0){
			add(new Label("You have been killed!\nWould you like to submit your high score?", skin));
			row();
			add(nameTextfield);
		}else
			add(new Label("You have been hit!", skin));
		row();
		add(okButton);
		row();
		if(lives <= 0){
			add(noButton);
			row();
		}
		add(exitButton);
		pack();
		x = Gdx.graphics.getWidth()/2 - width/2;
		y = Gdx.graphics.getHeight()/2 - height/2;
	}
}
