package com.blastedstudios.borderjumpingbonanza.ui;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.blastedstudios.borderjumpingbonanza.BorderJumpingBonanza;
import com.blastedstudios.borderjumpingbonanza.game.HighScoreStruct;
import com.blastedstudios.borderjumpingbonanza.game.Scores;

public class HighScoreScreen extends AbstractScreen {

	public HighScoreScreen(final BorderJumpingBonanza game){
		super(game);
		Window window = new Window("High Scores", skin);
		final Button newButton = new TextButton("New Game", skin.getStyle(TextButtonStyle.class), "ok");
		final Button backButton = new TextButton("Back", skin.getStyle(TextButtonStyle.class), "back");
		newButton.setClickListener(new ClickListener() {
			@Override public void click(Actor actor, float arg1, float arg2) {
				game.setScreen(new GameplayScreen(game));
			}
		});
		backButton.setClickListener(new ClickListener() {
			@Override public void click(Actor arg0, float arg1, float arg2) {
				game.setScreen(new MainScreen(game));
			}
		});
		window.add(new Label("#", skin)).pad(6);
		window.add(new Label("Name", skin)).pad(6);
		window.add(new Label("Score", skin)).pad(6);
		window.row();
		List<HighScoreStruct> highScores = Scores.getHighScores();
		for(int i=0; i<10; i++){
			HighScoreStruct struct = highScores.size()>i ? highScores.get(i) : new HighScoreStruct("",0);
			window.add(new Label(i + ". ", skin));
			window.add(new Label(struct.name, skin));
			window.add(new Label(struct.score+"", skin));
			window.row();
		}
		window.add(newButton).colspan(3);
		window.row();
		window.add(backButton).colspan(3);
		window.pack();
		window.x = Gdx.graphics.getWidth()/2 - window.width/2;
		window.y = Gdx.graphics.getHeight()/2 - window.height/2;
		stage.addActor(window);
	}
}
