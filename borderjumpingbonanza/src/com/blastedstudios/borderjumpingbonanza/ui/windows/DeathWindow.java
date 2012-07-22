package com.blastedstudios.borderjumpingbonanza.ui.windows;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.blastedstudios.borderjumpingbonanza.ui.GameplayScreen;

public class DeathWindow extends Window {
	private final GameplayScreen screen;
	private final TextField nameTextfield;
	
	public DeathWindow(final Skin skin, final GameplayScreen screen){
		super("Death", skin);
		this.screen = screen;
		nameTextfield = new TextField("Anonymous", skin);
		final Button okButton = new TextButton("Ok", skin.getStyle(TextButtonStyle.class), "ok");
		final Button noButton = new TextButton("No", skin.getStyle(TextButtonStyle.class), "no");
		final Button exitButton = new TextButton("Exit", skin.getStyle(TextButtonStyle.class), "exit");
		okButton.setClickListener(new ClickListener() {
			@Override public void click(Actor actor, float arg1, float arg2) {
				actor.getStage().removeActor(actor.parent);
				if(screen.getLives() <= 0)
					submitScore(nameTextfield.getText());
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
				Gdx.app.exit();
			}
		});
		if(screen.getTimeRemaining() <= 0)
			add(new Label("You have run out of time!", skin));
		else if(screen.getLives() <= 0){
			add(new Label("You have been killed!\nWould you like to submit your high score?", skin));
			row();
			add(nameTextfield);
		}else
			add(new Label("You have been hit!", skin));
		row();
		add(okButton);
		row();
		if(screen.getTimeRemaining() <= 0){
			add(noButton);
			row();
		}
		add(exitButton);
		pack();
		x = Gdx.graphics.getWidth()/2 - width/2;
		y = Gdx.graphics.getHeight()/2 - height/2;
	}
	
	private boolean submitScore(String name){
		try{
			DatagramSocket socket = new DatagramSocket();
	        InetAddress serverIP = InetAddress.getByName("jrob.no-ip.org");
	        String data = name + "-" + screen.getScore();
	        byte[] outData = data.getBytes();
	        DatagramPacket out = new DatagramPacket(outData, outData.length, serverIP, 58392);
	        socket.send(out);
	        socket.close();
		}catch(Exception e){
        	return false;
        }
        return true;
	}
}
