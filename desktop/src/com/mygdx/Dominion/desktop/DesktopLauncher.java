package com.mygdx.Dominion.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.Dominion.UI.DominionUI;
import com.mygdx.Dominion.UI.UIConfig;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) UIConfig.width;
		config.height = (int) UIConfig.height;
		new LwjglApplication(new DominionUI(), config);
	}
}
