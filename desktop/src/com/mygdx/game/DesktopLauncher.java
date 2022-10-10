package com.mygdx.game;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import com.mygdx.game.SpiderEnemies;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
                config.setWindowedMode(SpiderEnemies.WIDTH, SpiderEnemies.HEIGHT);
		config.setTitle("Spider-Enemies");
                config.setWindowIcon(FileType.Internal, "icons/icon_128x128.png");
                config.setWindowIcon(FileType.Internal, "icons/icon_32x32.png");
                config.setWindowIcon(FileType.Internal, "icons/icon_16x16.png");
		new Lwjgl3Application(new SpiderEnemies(), config);
	}
}
