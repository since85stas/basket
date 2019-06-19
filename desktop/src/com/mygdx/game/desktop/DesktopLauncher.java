package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.TekerBasket;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new TekerBasket(), config) {

		};

		config.foregroundFPS = 100;
		config.backgroundFPS = 100;
		config.width = 1500;
		config.height = 600;
	}
}
