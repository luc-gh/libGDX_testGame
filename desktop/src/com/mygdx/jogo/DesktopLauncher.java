package com.mygdx.jogo;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);  //definição da taxa de FPS da janela
		config.setTitle("Jogo das bolinhas"); //definição de nome da janela do jogo

		config.setWindowedMode(800,600);  //altera dimensões da janela (tela)
		config.setResizable(false);  //impede o redimensionamento da janela (tela)

		new Lwjgl3Application(new com.mygdx.jogo.jogo(), config);
	}
}