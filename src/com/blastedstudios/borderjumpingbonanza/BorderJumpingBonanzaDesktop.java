package com.blastedstudios.borderjumpingbonanza;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class BorderJumpingBonanzaDesktop {
	public static void main (String[] argv) {
		new JoglApplication(new BorderJumpingBonanza(), "Border Jumping Bonanza", 800, 600, false);
	}
}
