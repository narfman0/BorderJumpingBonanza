package com.blastedstudios.borderjumpingbonanza;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import android.os.Bundle;

public class BorderJumpingBonanzaAndroid extends AndroidApplication {
	@Override public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize(new BorderJumpingBonanza(), new AndroidApplicationConfiguration());
	}
}
