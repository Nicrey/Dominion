package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import UI.DominionGame;
import UI.UIConfig;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) UIConfig.menuWidth;
		config.height = (int) UIConfig.menuHeight;
		config.resizable = false;
		new LwjglApplication(new DominionGame(), config);
		
		
	}
}
