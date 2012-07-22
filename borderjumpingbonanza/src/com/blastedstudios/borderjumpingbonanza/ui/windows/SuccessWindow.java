package com.blastedstudios.borderjumpingbonanza.ui.windows;

import com.badlogic.gdx.scenes.scene2d.ui.Window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.blastedstudios.borderjumpingbonanza.ui.GameplayScreen;

public class SuccessWindow extends Window {
	public SuccessWindow(final Skin skin, final GameplayScreen screen){
		super("Success", skin);
		final Button okButton = new TextButton("Ok", skin.getStyle(TextButtonStyle.class), "ok");
		final Button exitButton = new TextButton("Exit", skin.getStyle(TextButtonStyle.class), "exit");
		okButton.setClickListener(new ClickListener() {
			@Override public void click(Actor actor, float arg1, float arg2) {
				actor.getStage().removeActor(actor.parent);
				screen.newLevel();
			}
		});
		exitButton.setClickListener(new ClickListener() {
			@Override public void click(Actor arg0, float arg1, float arg2) {
				Gdx.app.exit();
			}
		});
		row();
		add(new Label("You have successfully crossed the border!", skin));
		row();
		add(okButton);
		row();
		add(exitButton);
		pack();
		x = Gdx.graphics.getWidth()/2 - width/2;
		y = Gdx.graphics.getHeight()/2 - height/2;
	}
}
