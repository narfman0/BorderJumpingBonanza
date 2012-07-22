package com.blastedstudios.borderjumpingbonanza.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.blastedstudios.borderjumpingbonanza.BorderJumpingBonanza;

public class MainScreen extends AbstractScreen {
	public MainScreen(final BorderJumpingBonanza game){
		super(game);
		final Button newGameButton = new TextButton("New Game", skin.getStyle(TextButtonStyle.class), "new");
		final Button highScoresButton = new TextButton("High Scores", skin.getStyle(TextButtonStyle.class), "high");
		final Button exitButton = new TextButton("Exit", skin.getStyle(TextButtonStyle.class), "exit");
		newGameButton.setClickListener(new ClickListener() {
			@Override public void click(Actor arg0, float arg1, float arg2) {
				game.setScreen(new GameplayScreen(game));
			}
		});
		highScoresButton.setClickListener(new ClickListener() {
			@Override public void click(Actor actor, float arg1, float arg2) {
				game.setScreen(new HighScoreScreen(game));
			}
		});
		exitButton.setClickListener(new ClickListener() {
			@Override public void click(Actor arg0, float arg1, float arg2) {
				Gdx.app.exit();
			}
		});
		Window window = new Window("Border Jumping Bonanza", skin.getStyle(WindowStyle.class), "window");
		window.add(newGameButton);
		window.row();
		window.add(highScoresButton);
		window.row();
		window.add(exitButton);
		window.pack();
		window.x = Gdx.graphics.getWidth()/2 - window.width/2;
		window.y = Gdx.graphics.getHeight()/2 - window.height/2;
		stage.addActor(window);
	}
}
