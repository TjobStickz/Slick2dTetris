/**
 * 
 */
package com.aj.slick2d.tetris;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import com.aj.slick2d.tetris.core.Game;

/**
 * Game launcher.
 * 
 * @author BETON
 */
public class Launcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Game());

			app.setAlwaysRender(true);
			app.setDisplayMode(1024, 768, false);
			app.setTargetFrameRate(100);

			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
